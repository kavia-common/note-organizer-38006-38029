package com.example.notesbackend.service;

import com.example.notesbackend.dto.NoteCreateRequest;
import com.example.notesbackend.dto.NoteUpdateRequest;
import com.example.notesbackend.model.Note;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Notes.
 */
public interface NoteService {

    // PUBLIC_INTERFACE
    /**
     * Creates a new note.
     * @param request create payload
     * @return persisted Note
     */
    Note create(NoteCreateRequest request);

    // PUBLIC_INTERFACE
    /**
     * Returns all notes.
     * @return list of notes
     */
    List<Note> findAll();

    // PUBLIC_INTERFACE
    /**
     * Finds a note by id.
     * @param id note id
     * @return optional Note
     */
    Optional<Note> findById(Long id);

    // PUBLIC_INTERFACE
    /**
     * Updates a note with provided fields.
     * @param id note id
     * @param request update payload
     * @return updated Note
     */
    Note update(Long id, NoteUpdateRequest request);

    // PUBLIC_INTERFACE
    /**
     * Deletes a note by id.
     * @param id note id
     */
    void deleteById(Long id);
}
