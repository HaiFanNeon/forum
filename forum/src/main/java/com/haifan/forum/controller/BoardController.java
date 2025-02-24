package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.Board;
import com.haifan.forum.service.IBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
@Api(tags = "板块接口")
public class BoardController {
    // 配置默认值，如果没有就为9
    @Value("${haifan-forum.index.board-num:9}")
    private Integer indexBoardNum;


    @Resource
    private IBoardService boardService;


    @ApiOperation("获取首页板块列表")
    @GetMapping("/topList")
    public List<Board> topList () {
        log.info("首页板块个数为: " + indexBoardNum);
        List<Board> boards = boardService.selectByNum(indexBoardNum);

        if (boards == null) {
            boards = new ArrayList<>();
        }

        return boards;
    }


    @GetMapping("/getById")
    @ApiOperation("获取板块信息")
    public Board getById(@ApiParam("boardId") @RequestParam("id") @NonNull Long id) {
        Board board = boardService.selectById(id);
        if (board == null || board.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_BOARD_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_NOT_EXISTS));
        }
        return board;
    }
}
