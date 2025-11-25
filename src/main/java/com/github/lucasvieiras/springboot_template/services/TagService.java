package com.github.lucasvieiras.springboot_template.services;

import com.github.lucasvieiras.springboot_template.dto.requests.tag.TagRequest;
import com.github.lucasvieiras.springboot_template.entities.Tag;
import com.github.lucasvieiras.springboot_template.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag createTag(TagRequest request) {
        Tag tag = Tag.builder()
                .value(request.value())
                .build();

        return tagRepository.save(tag);
    }

    public Tag findById(UUID id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Tag updateTag(TagRequest request, UUID id) {
        Tag Tag = tagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (request.value() != null) Tag.setValue(request.value());

        return tagRepository.save(Tag);
    }

    public Page<Tag> findAllCategories(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
    
    public Tag findTagByTitle(String title) {
        return tagRepository.findByValue(title).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteTag(UUID id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        tagRepository.delete(tag);
    }
}
