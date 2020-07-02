package com.example.demo.controller;
import com.example.demo.model.SpringBootDemoModel;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SpringBootDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/api")
public class SpringBootDemoController {

    @Autowired
    SpringBootDemoRepository repository;

    @GetMapping("/notes")
    public List<SpringBootDemoModel> getAllNotes() {
        return repository.findAll();
    }

    @PostMapping("/notes")
    public SpringBootDemoModel createNote(@Valid @RequestBody SpringBootDemoModel note) {
        return (SpringBootDemoModel) repository.save(note);
    }

    @GetMapping("/notes/{id}")
    public SpringBootDemoModel getNoteById(@PathVariable(value = "id") Long noteId) {
        return repository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @PutMapping("/notes/{id}")
    public SpringBootDemoModel updateNote(@PathVariable(value = "id") Long noteId,
                                           @Valid @RequestBody SpringBootDemoModel noteDetails) {

        Note note = repository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        SpringBootDemoModel updatedNote = repository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
    	SpringBootDemoModel note = repository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        repository.delete(note);

        return ResponseEntity.ok().build();
    }
}