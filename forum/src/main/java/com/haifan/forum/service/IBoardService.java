package com.haifan.forum.service;

import com.haifan.forum.model.Board;

import java.util.List;

public interface IBoardService {

    /**
     * 展示出来的导航栏信息
     * @param num 要查询的记录数
     * @return
     */
    List<Board> selectByNum(Integer num);

}
