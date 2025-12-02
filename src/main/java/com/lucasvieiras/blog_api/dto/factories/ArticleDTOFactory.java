package com.lucasvieiras.blog_api.dto.factories;

import com.lucasvieiras.blog_api.dto.ArticleDTO;
import com.lucasvieiras.blog_api.entities.Article;
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
