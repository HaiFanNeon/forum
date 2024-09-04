package com.rainbow.service.impl;

import com.rainbow.entity.Board;
import com.rainbow.mapper.BoardMapper;
import com.rainbow.service.IBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rainbow-Su
 * @since 2024-08-23
 */
@Service
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements IBoardService {

}
