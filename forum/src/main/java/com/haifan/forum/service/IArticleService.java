package com.haifan.forum.service;

import com.haifan.forum.model.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleService {

    /**
     * 发布帖子
     * @param article 要发布的帖子
     */
    @Transactional
    void create (Article article);


    /**
     * 查询所有帖子
     * @return
     */
    List<Article> selectAll();

    /**
     * 根据id查询帖子详情
     * @param id
     * @return
     */
    @Transactional
    Article selectDetailById(Long id);

    /**
     * 编辑帖子
     * @param id 帖子id
     * @param title 修改后的帖子内容
     * @param content 修改后的内容
     */
    void modify (Long id, String title, String content);

    Article selectById(Long id);

    void thumbsUpById(Long id);

    /**
     * 根据id删除帖子
     * @param id
     */
    @Transactional
    void deleteById(Long id);


    /**
     * 板块中的回复数量 + 1
     * @param id
     */
    void addOneReplyCountById(Long id);

    /**
     * 根据用户Id查询帖子列表
     * @param userId
     * @return
     */
    List<Article> selectByUserId (@Param("userId") Long userId);
}
