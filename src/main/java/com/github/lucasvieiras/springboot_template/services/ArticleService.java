package com.github.lucasvieiras.springboot_template.services;

import com.github.lucasvieiras.springboot_template.dto.requests.article.ArticleRequest;
import com.github.lucasvieiras.springboot_template.dto.requests.article.CreateArticleRequest;
import com.github.lucasvieiras.springboot_template.entities.Article;
import com.github.lucasvieiras.springboot_template.repositories.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article createArticle(CreateArticleRequest request) {
        Article article = Article.builder()
                .title(request.title())
                .subtitle(request.subtitle())
                .tags(request.tags())
                .categories(request.categories())
                .build();
        return articleRepository.save(article);
    }

    public Article updateArticle(ArticleRequest request, UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (request.title() != null) article.setTitle(request.title());

        return articleRepository.save(article);
    }

    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Article findById(UUID id) {
        return articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Article findByTitle(String title) {
        return articleRepository.findByTitle(title).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteArticle(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        articleRepository.delete(article);
    }
}
