package com.example.notesbackend.service.impl;

import com.example.notesbackend.dto.NoteCreateRequest;
import com.example.notesbackend.dto.NoteUpdateRequest;
import com.example.notesbackend.model.Note;
import com.example.notesbackend.repository.NoteRepository;
import com.example.notesbackend.service.NoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation of NoteService using Spring Data JPA.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository repository;

    public NoteServiceImpl(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Note create(NoteCreateRequest request) {
        Note n = new Note();
        n.setTitle(request.getTitle());
        n.setContent(request.getContent());
        n.setTags(request.getTags());
        return repository.save(n);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Note> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Note update(Long id, NoteUpdateRequest request) {
        Note existing = repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Note " + id + " not found")
        );
        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        existing.setTags(request.getTags());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Note " + id + " not found");
        }
        repository.deleteById(id);
    }
}
