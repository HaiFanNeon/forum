package com.haifan.forum.service.impl;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.dao.ArticleMapper;
import com.haifan.forum.dao.BoardMapper;
import com.haifan.forum.dao.UserMapper;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.Article;
import com.haifan.forum.model.Board;
import com.haifan.forum.model.User;
import com.haifan.forum.service.IArticleService;
import com.haifan.forum.service.IBoardService;
import com.haifan.forum.service.IUserService;
import com.haifan.forum.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImple implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private IUserService userService;

    @Resource
    private IBoardService boardService;

    @Override
    public void create(Article article) {
        if (article == null
                || article.getUserId() == null
                || article.getBoardId() == null
                || StringUtil.isEmpty(article.getTitle())
                || StringUtil.isEmpty(article.getContent())) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }


        article.setVisitCount(0);
        article.setReplyCount(0);
        article.setLikeCount(0);
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);

        int i = articleMapper.insertSelective(article);
        if (i <= 0) {
            log.warn(ResultCode.FAILED_CREATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }

        User user = userService.selectById(article.getUserId());
        if (user == null) {
            log.warn(ResultCode.FAILED_CREATE.getMessage() + " : " + article.getUserId());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        userService.addOneArticleCountById(user.getId());


        Board board = boardService.selectById(article.getBoardId());
        if (board == null) {
            log.warn(ResultCode.FAILED_CREATE.getMessage() + " : " + article.getBoardId());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        boardService.addOneArticleCountById(article.getBoardId());

        log.info(ResultCode.SUCCESS.getMessage() + "| [UserId] : "
                + article.getUserId()
                + "/n [BoardId] : " + article.getBoardId() + "/n 发帖成功");

    }

    @Override
    public List<Article> selectAll() {
        return articleMapper.selectAll();
    }
}
