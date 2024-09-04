package com.rainbow.service.impl;

import com.rainbow.entity.Article;
import com.rainbow.mapper.ArticleMapper;
import com.rainbow.service.IArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
