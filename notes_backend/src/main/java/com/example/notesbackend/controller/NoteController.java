package com.example.notesbackend.controller;

import com.example.notesbackend.dto.NoteCreateRequest;
import com.example.notesbackend.dto.NoteUpdateRequest;
import com.example.notesbackend.model.Note;
import com.example.notesbackend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller exposing CRUD operations for Notes.
 */
@RestController
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "CRUD operations for managing notes")
@CrossOrigin // Basic CORS for browser clients; can be narrowed as needed
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(
            summary = "List notes",
            description = "Returns all notes sorted by id ascending",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of notes",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Note.class)))
            }
    )
    public List<Note> getNotes() {
        List<Note> notes = service.findAll();
        notes.sort(Comparator.comparing(Note::getId));
        return notes;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(
            summary = "Get note by id",
            description = "Returns a single note by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Note found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            }
    )
    public ResponseEntity<Note> getNote(
            @Parameter(description = "Note id", required = true) @PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(
            summary = "Create note",
            description = "Creates a new note with title, content, and optional tags",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Note created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            }
    )
    public ResponseEntity<Note> createNote(@Valid @RequestBody NoteCreateRequest request) {
        Note created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(
            summary = "Update note",
            description = "Updates title, content, and tags of the specified note",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Note updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            }
    )
    public ResponseEntity<?> updateNote(
            @Parameter(description = "Note id", required = true) @PathVariable Long id,
            @Valid @RequestBody NoteUpdateRequest request) {
        try {
            Note updated = service.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "not_found", "message", e.getMessage()));
        }
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete note",
            description = "Deletes the note with the specified id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Note deleted"),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            }
    )
    public ResponseEntity<?> deleteNote(
            @Parameter(description = "Note id", required = true) @PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "not_found", "message", e.getMessage()));
        }
    }

    /**
     * Handle bean validation errors (400).
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Map<String, Object>> handleValidation(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "validation_error");
        List<Map<String, String>> fieldErrors = new ArrayList<>();
        if (ex instanceof MethodArgumentNotValidException manve) {
            manve.getBindingResult().getFieldErrors().forEach(err ->
                    fieldErrors.add(Map.of("field", err.getField(), "message", err.getDefaultMessage()))
            );
        } else if (ex instanceof BindException be) {
            be.getBindingResult().getFieldErrors().forEach(err ->
                    fieldErrors.add(Map.of("field", err.getField(), "message", err.getDefaultMessage()))
            );
        }
        body.put("fieldErrors", fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }
}
