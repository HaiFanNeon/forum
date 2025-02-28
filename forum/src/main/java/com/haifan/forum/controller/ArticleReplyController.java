package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.model.Article;
import com.haifan.forum.model.ArticleReply;
import com.haifan.forum.service.IArticleReplyService;
import com.haifan.forum.service.IArticleService;
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
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articleReply")
@Api(tags = "回帖接口")
public class ArticleReplyController {

    @Resource
    private IArticleReplyService articleReplyService;

    @Resource
    private IArticleService articleService;

    @PostMapping("/create")
    @ApiOperation("回复帖子")
    public AppResult create (HttpServletRequest request,
                             @ApiParam("articleId") @RequestParam("articleId") @NonNull Long articleId,
                             @ApiParam("content") @RequestParam("content") @NonNull String content) {
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

        Article article = articleService.selectById(articleId);
        if (article == null || article.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.getMessage());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }

        if (article.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }

        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleId(articleId);
        articleReply.setPostUserId(Long.valueOf(userId));
        articleReply.setContent(content);
        articleReply.setUpdateTime(new Date());

        articleReplyService.create(articleReply);
        return AppResult.success();
    }


    @ApiOperation("获取回复列表")
    @GetMapping("/getRelies")
    public AppResult getRepliesByArticleId (@ApiParam("articleId") @RequestParam("articleId") @NonNull Long articleId) {

        Article article = articleService.selectById(articleId);
        if (article == null || article.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }

        List<ArticleReply> articleReplies = articleReplyService.selectByArticleId(articleId);

        return AppResult.success(articleReplies);
    }

}
