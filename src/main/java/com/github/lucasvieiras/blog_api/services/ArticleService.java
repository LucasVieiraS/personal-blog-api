package com.github.lucasvieiras.blog_api.services;

import com.github.lucasvieiras.blog_api.dto.requests.article.ArticleRequest;
import com.github.lucasvieiras.blog_api.dto.requests.article.CreateArticleRequest;
import com.github.lucasvieiras.blog_api.entities.Article;
import com.github.lucasvieiras.blog_api.repositories.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
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

    public Page<Article> findByTagValues(Collection<String> values, boolean matchAll, Pageable pageable) {
        if (values == null || values.isEmpty()) {
            return Page.empty(pageable);
        }

        if (matchAll) {
            return articleRepository.findByAllTagValues(values, values.size(), pageable);
        }

        return articleRepository.findByAnyTagValues(values, pageable);
    }

    public Page<Article> findByCategoryValues(Collection<String> values, boolean matchAll, Pageable pageable) {
        if (values == null || values.isEmpty()) {
            return Page.empty(pageable);
        }

        if (matchAll) {
            return articleRepository.findByAllCategoryValues(values, values.size(), pageable);
        }
        
        return articleRepository.findByAnyCategoryValues(values, pageable);
    }
}
