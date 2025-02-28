package com.haifan.forum.service;

import com.haifan.forum.model.ArticleReply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleReplyService {


    /**
     * 新增回复
     * @param articleReply
     */
    @Transactional
    void create (ArticleReply articleReply);

    /**
     * 根据id查询帖子回复数量
     * @param articleId
     * @return
     */
    List<ArticleReply> selectByArticleId(Long articleId);
}
