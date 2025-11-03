package com.example.notesbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating an existing Note.
 */
public class NoteUpdateRequest {

    @Schema(description = "Title of the note", example = "Updated Meeting Notes", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "title is required")
    @Size(max = 255, message = "title must be at most 255 characters")
    private String title;

    @Schema(description = "Content of the note", example = "Added action items", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "content is required")
    private String content;

    @Schema(description = "Optional comma-separated tags", example = "work,planning,action")
    @Size(max = 512, message = "tags must be at most 512 characters")
    private String tags;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public NoteUpdateRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteUpdateRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public NoteUpdateRequest setTags(String tags) {
        this.tags = tags;
        return this;
    }
}
