package com.haifan.forum.service.impl;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.dao.BoardMapper;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.Board;
import com.haifan.forum.service.IBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class BoardServiceImpl implements IBoardService {

    @Resource
    private BoardMapper boardMapper;



    @Override
    public List<Board> selectByNum(Integer num) {
        if (num == null || num <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return boardMapper.selectByNum(num);

    }

    @Override
    public void addOneArticleCountById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

        Board board = boardMapper.selectByPrimaryKey(id);
        if (board == null) {
            log.warn(ResultCode.ERROR_IS_NULL.getMessage() + " : " + id.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        Board updateBoard = new Board();
        updateBoard.setId(board.getId());
        updateBoard.setUpdateTime(new Date());
        updateBoard.setArticleCount(board.getArticleCount() + 1);
        int i = boardMapper.updateByPrimaryKeySelective(updateBoard);
        if (i != 1) {
            log.warn(ResultCode.FAILED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }

    @Override
    public Board selectById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Board board = boardMapper.selectByPrimaryKey(id);
        return board;
    }
}
