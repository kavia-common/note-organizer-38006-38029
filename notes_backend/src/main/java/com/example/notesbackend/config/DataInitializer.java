package com.example.notesbackend.config;

import com.example.notesbackend.model.Note;
import com.example.notesbackend.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds database with example notes on application startup.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedNotes(NoteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Note n1 = new Note();
                n1.setTitle("Welcome to Notes");
                n1.setContent("This is your first note. Feel free to edit or delete it.");
                n1.setTags("welcome,getting-started");
                repository.save(n1);

                Note n2 = new Note();
                n2.setTitle("Todo");
                n2.setContent("- Write docs\n- Ship API\n- Celebrate");
                n2.setTags("todo,planning");
                repository.save(n2);
            }
        };
    }
}
