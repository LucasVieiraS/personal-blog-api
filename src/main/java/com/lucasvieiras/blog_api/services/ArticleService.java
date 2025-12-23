package com.lucasvieiras.blog_api.services;

import com.lucasvieiras.blog_api.dtos.requests.article.ArticleRequest;
import com.lucasvieiras.blog_api.dtos.requests.article.CreateArticleRequest;
import com.lucasvieiras.blog_api.entities.Article;
import com.lucasvieiras.blog_api.entities.User;
import com.lucasvieiras.blog_api.exceptions.ConflictException;
import com.lucasvieiras.blog_api.exceptions.ResourceNotFoundException;
import com.lucasvieiras.blog_api.repositories.ArticleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Transactional
    public Article createArticle(CreateArticleRequest request) {
        if (articleRepository.findByTitle(request.title()).isPresent()) {
            throw new ConflictException("Article already exists");
        }

        User author = userService.findUserById(request.authorId()).orElseThrow(
                () -> new ResourceNotFoundException("Author not found")
        );

        Article article = Article.builder()
                .title(request.title())
                .subtitle(request.subtitle())
                .tags(request.tags())
                .categories(request.categories())
                .author(author)
                .build();

        return articleRepository.save(article);
    }

    @Transactional
    public Article updateArticle(ArticleRequest request, UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        if (request.title() != null) article.setTitle(request.title());

        return articleRepository.save(article);
    }

    @Transactional
    public void deleteArticle(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Article findById(UUID id) {
        return articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Article findByTitle(String title) {
        return articleRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Article not found with title: " + title));
    }

    @Transactional(readOnly = true)
    public Page<Article> findByTagValues(Collection<String> values, boolean matchAll, Pageable pageable) {
        if (values == null || values.isEmpty()) {
            return Page.empty(pageable);
        }

        if (matchAll) {
            return articleRepository.findByAllTagValues(values, values.size(), pageable);
        }

        return articleRepository.findByAnyTagValues(values, pageable);
    }

    @Transactional(readOnly = true)
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
