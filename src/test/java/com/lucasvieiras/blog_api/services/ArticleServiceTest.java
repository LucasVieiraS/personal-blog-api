package com.lucasvieiras.blog_api.services;

import com.lucasvieiras.blog_api.dtos.requests.article.CreateArticleRequest;
import com.lucasvieiras.blog_api.entities.Article;
import com.lucasvieiras.blog_api.entities.User;
import com.lucasvieiras.blog_api.exceptions.ConflictException;
import com.lucasvieiras.blog_api.exceptions.ResourceNotFoundException;
import com.lucasvieiras.blog_api.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ArticleService articleService;

    private UUID mockArticleId = UUID.randomUUID();
    private UUID mockAuthorId = UUID.randomUUID();
    private Article mockArticle;
    private User mockUser;
    private CreateArticleRequest mockRequest;

    @BeforeEach
    public void setUp() {
        mockUser = User.builder()
                .id(mockAuthorId)
                .build();

        mockArticle = Article.builder()
                .id(mockArticleId)
                .title("Article title")
                .author(mockUser)
                .build();

        mockRequest = new CreateArticleRequest(
                mockArticle.getTitle(),
                mockArticle.getSubtitle(),
                mockArticle.getTags(),
                mockArticle.getCategories(),
                mockArticle.getContentMarkdown(),
                mockUser.getId()
        );
    }

    @Test
    void createArticle_success_shouldCreateArticleAndSave() {
        // ARRANGE - Behavior of mocks
        when(articleRepository.findByTitle(mockRequest.title())).thenReturn(Optional.empty());
        when(userService.existsById(mockAuthorId)).thenReturn(Boolean.TRUE);
        when(articleRepository.save(any(Article.class))).thenReturn(mockArticle);

        // ACT - Happy path
        Article result = articleService.createArticle(mockRequest);

        // ASSERT - Assertions
        assertNotNull(result);
        assertEquals(mockArticle.getTitle(), result.getTitle());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void createArticle_titleConflict_shouldThrowConflictException() {
        // ARRANGE
        when(articleRepository.findByTitle(mockRequest.title())).thenReturn(Optional.of(mockArticle));

        // ACT - Exception path
        assertThrows(ConflictException.class, () -> {
            articleService.createArticle(mockRequest);
        });

        // ASSERT
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void createArticle_authorNotFound_shouldThrowResourceNotFoundException() {
        // ARRANGE
        when(articleRepository.findByTitle(mockRequest.title())).thenReturn(Optional.empty());

        // ACT
        assertThrows(ResourceNotFoundException.class, () -> {
            articleService.createArticle(mockRequest);
        });

        // ASSERT
        verify(articleRepository, never()).save(any(Article.class));
    }
}
