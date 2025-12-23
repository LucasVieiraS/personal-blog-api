package com.lucasvieiras.blog_api.services;

import com.lucasvieiras.blog_api.dtos.requests.tag.TagRequest;
import com.lucasvieiras.blog_api.entities.Tag;
import com.lucasvieiras.blog_api.exceptions.BadRequestException;
import com.lucasvieiras.blog_api.exceptions.ConflictException;
import com.lucasvieiras.blog_api.exceptions.ResourceNotFoundException;
import com.lucasvieiras.blog_api.repositories.TagRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Tag createTag(TagRequest request) {
        if (tagRepository.findByValue(request.value()).isPresent()) {
            throw new ConflictException("Tag already exists");
        }

        Tag tag = Tag.builder()
                .value(request.value())
                .build();

        return tagRepository.save(tag);
    }

    @Transactional
    public Tag updateTag(TagRequest request, UUID id) {
        Tag Tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        if (request.value() != null) Tag.setValue(request.value());

        return tagRepository.save(Tag);
    }

    @Transactional
    public void deleteTag(UUID id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        if (!tag.getArticles().isEmpty()) {
            throw new BadRequestException("Cannot delete tag that has articles assigned");
        }

        tagRepository.delete(tag);
    }

    @Transactional(readOnly = true)
    public Tag findById(UUID id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Tag> findAllCategories(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Tag findTagByTitle(String title) {
        return tagRepository.findByValue(title).orElseThrow(() -> new ResourceNotFoundException("Tag not found with title: " + title));
    }
}
