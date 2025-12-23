package com.lucasvieiras.blog_api.controllers;

import com.lucasvieiras.blog_api.dtos.factories.TagDTOFactory;
import com.lucasvieiras.blog_api.dtos.TagDTO;
import com.lucasvieiras.blog_api.dtos.requests.tag.TagRequest;
import com.lucasvieiras.blog_api.entities.Tag;
import com.lucasvieiras.blog_api.services.TagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagDTOFactory tagDTOFactory;

    @PostMapping()
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagRequest request) {
        Tag tag = tagService.createTag(request);
        TagDTO response = tagDTOFactory.create(tag);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@Valid @RequestBody TagRequest request, @PathVariable UUID id) {
        Tag tag = tagService.updateTag(request, id);
        TagDTO response = tagDTOFactory.create(tag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<Page<TagDTO>> findAllTags(Pageable pageable) {
        Page<Tag> tagsPage = tagService.findAllCategories(pageable);

        List<TagDTO> tagDTOList = tagDTOFactory.create(tagsPage.getContent());
        Page<TagDTO> tagDTOPage = new PageImpl<>(tagDTOList, pageable, tagsPage.getTotalElements());

        return new ResponseEntity<>(tagDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> findTagById(@PathVariable UUID id) {
        Tag tag = tagService.findById(id);
        TagDTO response = tagDTOFactory.create(tag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<TagDTO> findTagByTitle(@PathVariable String title) {
        Tag tag = tagService.findTagByTitle(title);
        TagDTO response = tagDTOFactory.create(tag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
