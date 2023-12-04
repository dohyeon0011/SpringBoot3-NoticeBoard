package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository -> CrudRepository -> save()
public interface BlogRepository extends JpaRepository<Article, Long> {
}
