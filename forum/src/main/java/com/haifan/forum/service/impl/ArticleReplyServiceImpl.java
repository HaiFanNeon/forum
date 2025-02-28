package com.haifan.forum.service.impl;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.DatabindException;
import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.dao.ArticleReplyMapper;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.ArticleReply;
import com.haifan.forum.service.IArticleReplyService;
import com.haifan.forum.service.IArticleService;
import com.haifan.forum.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ArticleReplyServiceImpl implements IArticleReplyService {

    @Resource
    private ArticleReplyMapper articleReplyMapper;

    @Resource
    private IArticleService articleService;

    @Override
    public void create(ArticleReply articleReply) {
        if (articleReply == null || articleReply.getArticleId() == null || StringUtil.isEmpty(articleReply.getContent())) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        articleReply.setReplyId(null);
        articleReply.setReplyUserId(null);
        articleReply.setLikeCount(0);
        articleReply.setState((byte)0);
        articleReply.setDeleteState((byte)0);
        Date date = new Date();
        articleReply.setCreateTime(date);
        articleReply.setUpdateTime(date);

        int i = articleReplyMapper.insertSelective(articleReply);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

        articleService.addOneReplyCountById(articleReply.getArticleId());

        log.info("回复成功, articleId : " + articleReply.getArticleId());
    }

    @Override
    public List<ArticleReply> selectByArticleId(Long articleId) {
        if (articleId == null || articleId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        List<ArticleReply> articleReplies = articleReplyMapper.selectByArticleId(articleId);

        return articleReplies;
    }
}
