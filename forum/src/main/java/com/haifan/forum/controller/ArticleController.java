package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
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
}
