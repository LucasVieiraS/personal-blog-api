package com.lucasvieiras.blog_api.controllers;

import com.lucasvieiras.blog_api.dtos.factories.ArticleDTOFactory;
import com.lucasvieiras.blog_api.dtos.ArticleDTO;
import com.lucasvieiras.blog_api.dtos.requests.article.ArticleRequest;
import com.lucasvieiras.blog_api.dtos.requests.article.CreateArticleRequest;
import com.lucasvieiras.blog_api.entities.Article;
import com.lucasvieiras.blog_api.services.ArticleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleDTOFactory articleDTOFactory;

    @PostMapping()
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody CreateArticleRequest request) {
        Article article = articleService.createArticle(request);
        ArticleDTO response = articleDTOFactory.create(article);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@Valid @RequestBody ArticleRequest articleRequest, @PathVariable UUID id) {
        Article article =  articleService.updateArticle(articleRequest, id);
        ArticleDTO response = articleDTOFactory.create(article);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable UUID id) {
        articleService.deleteArticle(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<Page<ArticleDTO>> findAllArticles(Pageable pageable) {
        Page<Article> articlesPages = articleService.findAllArticles(pageable);

        List<ArticleDTO> articleDTOList = articleDTOFactory.create(articlesPages.getContent());
        Page<ArticleDTO> articlesDTOPage = new PageImpl<>(articleDTOList, pageable, articlesPages.getTotalElements());

        return new ResponseEntity<>(articlesDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> findArticleById(@PathVariable UUID id) {
        Article article = articleService.findById(id);
        ArticleDTO response = articleDTOFactory.create(article);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<ArticleDTO> findArticleByTitle(@PathVariable String title) {
        Article article = articleService.findByTitle(title);
        ArticleDTO response = articleDTOFactory.create(article);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter/tags")
    public ResponseEntity<Page<ArticleDTO>> findArticlesByTags(
            @RequestParam List<String> values,
            @RequestParam(defaultValue = "any") String match,
            Pageable pageable
    ) {
        boolean matchAll = "all".equalsIgnoreCase(match);
        Page<Article> articlesPage = articleService.findByTagValues(values, matchAll, pageable);

        List<ArticleDTO> articleDTOList = articleDTOFactory.create(articlesPage.getContent());
        Page<ArticleDTO> articlesDTOPage = new PageImpl<>(articleDTOList, pageable, articlesPage.getTotalElements());

        return new ResponseEntity<>(articlesDTOPage, HttpStatus.OK);
    }

    @GetMapping("/filter/categories")
    public ResponseEntity<Page<ArticleDTO>> findArticlesByCategories(
            @RequestParam List<String> values,
            @RequestParam(defaultValue = "any") String match,
            Pageable pageable
    ) {
        boolean matchAll = "all".equalsIgnoreCase(match);
        Page<Article> articlesPage = articleService.findByCategoryValues(values, matchAll, pageable);

        List<ArticleDTO> articleDTOList = articleDTOFactory.create(articlesPage.getContent());
        Page<ArticleDTO> articlesDTOPage = new PageImpl<>(articleDTOList, pageable, articlesPage.getTotalElements());

        return new ResponseEntity<>(articlesDTOPage, HttpStatus.OK);
    }
}
