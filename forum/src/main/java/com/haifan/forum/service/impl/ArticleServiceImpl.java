package com.haifan.forum.service.impl;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.dao.ArticleMapper;
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
public class ArticleServiceImpl implements IArticleService {

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

    @Override
    public Article selectDetailById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

        Article article = articleMapper.selectDetailById(id);
        if (article == null) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }
        log.info(article.getContent());
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setVisitCount(article.getVisitCount() + 1);
        int i = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

        article.setVisitCount(article.getVisitCount() + 1);

        return article;
    }

    @Override
    public void modify(Long id, String title, String content) {
        if (id == null || id <= 0 || StringUtil.isEmpty(title) || StringUtil.isEmpty(content)) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Article updateArticle = new Article();
        updateArticle.setId(id);
        updateArticle.setTitle(title);
        updateArticle.setContent(content);
        updateArticle.setUpdateTime(new Date());

        int i = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }

    @Override
    public Article selectById (Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void thumbsUpById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null || article.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }

        if (article.getState() == 1 ) {
            log.warn(ResultCode.FAILED_ARTICLE_BANNED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED));
        }

        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setLikeCount(article.getLikeCount() + 1);
        updateArticle.setUpdateTime(new Date());

        int i = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null || article.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_BOARD_NOT_EXISTS.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_NOT_EXISTS));
        }

        Article updateArticle = new Article();

        updateArticle.setId(article.getId());
        updateArticle.setDeleteState((byte)1);
        int i = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

        boardService.subOneArticleCountById(article.getBoardId());
        userService.subOneArticleCountById(article.getUserId());
        log.info("删除帖子成功 [boardId] : " + article.getBoardId() + " [userId] : " + article.getUserId());

    }

    @Override
    public void addOneReplyCountById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null || article.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_BOARD_NOT_EXISTS.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_NOT_EXISTS));
        }

        if (article.getState() == 1) {
            log.warn(ResultCode.FAILED_ARTICLE_BANNED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED));
        }

        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setReplyCount(article.getReplyCount() + 1);
        updateArticle.setUpdateTime(new Date());

        int i = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }

    @Override
    public List<Article> selectByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        User user = userService.selectById(userId);
        if (user == null) {
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }

        return articleMapper.selectByUserId(userId);
    }
}
