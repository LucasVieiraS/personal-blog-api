package com.lucasvieiras.blog_api.repositories;

import com.lucasvieiras.blog_api.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    Optional<Article> findByTitle(String title);

    @Query("select distinct a from Article a join a.tags t where t.value in :values")
    Page<Article> findByAnyTagValues(@Param("values") Collection<String> values, Pageable pageable);

    @Query("select a from Article a join a.tags t where t.value in :values group by a.id having count(distinct t.id) = :size")
    Page<Article> findByAllTagValues(@Param("values") Collection<String> values, @Param("size") long size, Pageable pageable);

    @Query("select distinct a from Article a join a.categories c where c.value in :values")
    Page<Article> findByAnyCategoryValues(@Param("values") Collection<String> values, Pageable pageable);

    @Query("select a from Article a join a.categories c where c.value in :values group by a.id having count(distinct c.id) = :size")
    Page<Article> findByAllCategoryValues(@Param("values") Collection<String> values, @Param("size") long size, Pageable pageable);
}
