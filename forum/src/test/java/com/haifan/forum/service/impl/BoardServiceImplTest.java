package com.haifan.forum.service.impl;

import com.haifan.forum.model.Board;
import com.haifan.forum.service.IBoardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class BoardServiceImplTest {

    @Resource
    private IBoardService boardService;

    @Test
    void selectByNum() {
        List<Board> boards = boardService.selectByNum(5);
        log.info(boards.toString());
    }

    @Test
    @Transactional
    void addOneArticleCountById() {
        boardService.addOneArticleCountById(1l);
    }
}