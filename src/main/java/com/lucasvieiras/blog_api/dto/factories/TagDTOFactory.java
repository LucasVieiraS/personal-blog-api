package com.lucasvieiras.blog_api.dto.factories;

import com.lucasvieiras.blog_api.dto.TagDTO;
import com.lucasvieiras.blog_api.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDTOFactory {
    public TagDTO create(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getValue()
        );
    }

    public List<TagDTO> create(List<Tag> tags) {
        return tags.stream().map(this::create).toList();
    }
}


