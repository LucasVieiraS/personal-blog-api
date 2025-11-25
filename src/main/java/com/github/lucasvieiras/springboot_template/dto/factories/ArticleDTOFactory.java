package com.github.lucasvieiras.springboot_template.dto.factories;

import com.github.lucasvieiras.springboot_template.dto.requests.ArticleDTO;
import com.github.lucasvieiras.springboot_template.entities.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleDTOFactory {
    public ArticleDTO create(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getSubtitle(),
                article.getTags(),
                article.getCategories(),
                article.getContentMarkdown()
        );
    }

    public List<ArticleDTO> create(List<Article> articles) {
        return articles.stream().map(this::create).toList();
    }
}
