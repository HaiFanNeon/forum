package com.haifan.forum.service.impl;

import com.haifan.forum.model.Article;
import com.haifan.forum.service.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceImpleTest {

    @Resource
    private IArticleService articleService;

    @Test
    @Transactional
    void create() {

        Article article = new Article();
        article.setUserId(1l);
        article.setTitle("test");
        article.setContent("testtest");
        articleService.create(article);

    }

    @Test
    void selectAll() {
        List<Article> articles = articleService.selectAll();
        System.out.println(articles.toString());
    }
}