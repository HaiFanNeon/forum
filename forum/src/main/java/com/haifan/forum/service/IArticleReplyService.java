package com.haifan.forum.service;

import com.haifan.forum.model.ArticleReply;
import org.springframework.transaction.annotation.Transactional;

public interface IArticleReplyService {


    /**
     * 新增回复
     * @param articleReply
     */
    @Transactional
    void create (ArticleReply articleReply);
}
