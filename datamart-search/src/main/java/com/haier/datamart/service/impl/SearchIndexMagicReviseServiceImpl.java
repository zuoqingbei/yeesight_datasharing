package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SearchIndexMagicRevise;
import com.haier.datamart.mapper.SearchIndexMagicReviseMapper;
import com.haier.datamart.service.ISearchIndexMagicReviseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 业务用户填写的指标业务逻辑订正表 需要审核服务实现类
 * @author zuoqb123
 * @date 2018-10-30
 */
@Service
@Transactional
public class SearchIndexMagicReviseServiceImpl extends ServiceImpl<SearchIndexMagicReviseMapper, SearchIndexMagicRevise> implements ISearchIndexMagicReviseService {

    @Autowired
    private SearchIndexMagicReviseMapper searchIndexMagicReviseMapper;
   
}
