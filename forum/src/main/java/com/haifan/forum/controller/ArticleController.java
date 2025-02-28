package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.BaseContext;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.Article;
import com.haifan.forum.model.Board;
import com.haifan.forum.service.IArticleService;
import com.haifan.forum.service.IBoardService;
import com.haifan.forum.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
@Slf4j
@Api(tags = "文章接口")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @Resource
    private IBoardService boardService;


    @ApiOperation("发布帖子")
    @PostMapping("/create")
    public AppResult create (@ApiParam("boardId") @RequestParam("boardId") @NonNull Long boardId,
                             @ApiParam("title") @RequestParam("title") @NonNull String title,
                             @ApiParam("content") @RequestParam("content") @NonNull String content,
                             HttpServletRequest request) {
        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        Claims claims = JWTUtil.parseToken(token);
        Integer t = (Integer)JWTUtil.getParam(claims, "state");
        Byte state = t.byteValue();
        Integer id = (Integer)JWTUtil.getParam(claims, "id");
        if (state == 1) {
            // 表示用户已经被禁言了
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        Board board = boardService.selectById(boardId.longValue());
        if (board.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_BOARD_BANNED);
        }


        Article article = new Article();
        article.setBoardId(boardId);
        article.setTitle(title);
        article.setContent(content);
        article.setUserId(id.longValue());
        article.setUpdateTime(new Date());
        article.setCreateTime(new Date());

        articleService.create(article);

        return AppResult.success();
    }
    @GetMapping("/getAllByBoardId")
    @ApiOperation("查询帖子列表")
    public List<Article> getAllByBoardId(@ApiParam("boardId") @RequestParam(value = "boardId", required = false) Long boardId) {

        List<Article> articles = articleService.selectAll();
        if (articles == null) {
            articles = new ArrayList<>();
        }

        if (boardId != null) {
            log.info(boardId.toString());
            List<Article> collect = articles.stream().filter(article -> boardId.equals(article.getBoardId())).collect(Collectors.toList());
            log.info(articles.toString());
            if (collect == null) {
                return new ArrayList<>();
            }
            return collect;
        }

        return articles;
    }

    @GetMapping("/getDetail")
    @ApiOperation("根据id查询帖子详情")
    public Article getDetails (@ApiParam("articleId") @RequestParam("articleId") @NonNull Long id,
                               HttpServletRequest request) {
        Article article = articleService.selectDetailById(id);
        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer t = (Integer) JWTUtil.getParam(claims, "id");
        Long userId = t.longValue();


        log.info(userId.toString());
        if (article == null) {
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }

        if (Objects.equals(userId, article.getUserId())) {
            article.setOwn(true);
        }

        return article;
    }

    @PostMapping("/modify")
    @ApiOperation("编辑帖子")
    public AppResult modify(@ApiParam("id") @RequestParam("articleId") @NonNull Long id,
                       @ApiParam("title") @RequestParam("title") @NonNull String title,
                       @ApiParam("content") @RequestParam("content") @NonNull String content,
                       HttpServletRequest request) {
        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer t = (Integer)JWTUtil.getParam(claims, "state");
        Byte state = t.byteValue();
        Integer userId = (Integer)JWTUtil.getParam(claims, "id");
        if (state == 1) {
            // 表示用户已经被禁言了
            log.warn(ResultCode.FAILED_USER_BANNED.getMessage());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        Article article = articleService.selectById(id);
        if (article == null) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.getMessage());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }

        if (userId.longValue() != article.getUserId()) {
            log.warn(ResultCode.FAILED_FORBIDDEN.getMessage());
            return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
        }

        if (article.getState() == 1) {
            log.warn(ResultCode.FAILED_ARTICLE_BANNED.getMessage());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }

        articleService.modify(id, title, content);
        log.info("执行articleService.modify userId: " + id);
        return AppResult.success();
    }


    @ApiOperation("更新点赞数")
    @PostMapping("/thumbsUp")
    public AppResult thumbsUp (@ApiParam("articleId") @RequestParam("id") @NonNull Long id,
                               HttpServletRequest request) {
        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer t = (Integer)JWTUtil.getParam(claims, "state");
        Byte state = t.byteValue();
        Integer userId = (Integer)JWTUtil.getParam(claims, "id");
        if (state == 1) {
            // 表示用户已经被禁言了
            log.warn(ResultCode.FAILED_USER_BANNED.getMessage());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        Article article = articleService.selectById(id);
        if (article == null) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.getMessage());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }

        if (article.getState() == 1) {
            log.warn(ResultCode.FAILED_ARTICLE_BANNED.getMessage());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }

        articleService.thumbsUpById(id);
        return AppResult.success();
    }


    @ApiOperation("删除帖子")
    @PostMapping("/deleteById")
    public AppResult deleteById (@ApiParam("articleId") @RequestParam("id") @NonNull Long id,
                                 HttpServletRequest request) {


        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer t = (Integer)JWTUtil.getParam(claims, "state");
        Byte state = t.byteValue();
        Integer userId = (Integer)JWTUtil.getParam(claims, "id");
        if (state == 1) {
            // 表示用户已经被禁言了
            log.warn(ResultCode.FAILED_USER_BANNED.getMessage());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        Article article = articleService.selectById(id);
        if (article == null || article.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.getMessage());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }

        if (userId.longValue() != article.getUserId()) {
            log.warn(ResultCode.FAILED_FORBIDDEN.getMessage());
            return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
        }

        articleService.deleteById(id);
        return AppResult.success();
    }

    @GetMapping("/getAllByUserId")
    @ApiOperation("获取用户的帖子列表")
    public AppResult getAllByUserId (HttpServletRequest request,
                                     @ApiParam("userId") @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            String token = request.getHeader("user_token");

            if (token == null) token = request.getParameter("user_token");
            if (token == null) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("user_token")) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            Claims claims = JWTUtil.parseToken(token);
            Integer id = (Integer) JWTUtil.getParam(claims, "id");
            userId = id.longValue();
        }


        List<Article> articles = articleService.selectByUserId(userId);

        return AppResult.success(articles);
    }
}
