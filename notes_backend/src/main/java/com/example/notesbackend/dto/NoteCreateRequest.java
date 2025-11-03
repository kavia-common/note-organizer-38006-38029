package com.example.notesbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a Note.
 */
public class NoteCreateRequest {

    @Schema(description = "Title of the note", example = "Meeting Notes", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "title is required")
    @Size(max = 255, message = "title must be at most 255 characters")
    private String title;

    @Schema(description = "Content of the note", example = "Discussed roadmap and milestones", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "content is required")
    private String content;

    @Schema(description = "Optional comma-separated tags", example = "work,planning")
    @Size(max = 512, message = "tags must be at most 512 characters")
    private String tags;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public NoteCreateRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteCreateRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public NoteCreateRequest setTags(String tags) {
        this.tags = tags;
        return this;
    }
}
