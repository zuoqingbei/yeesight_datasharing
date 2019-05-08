package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SearchReportsFile;
import com.haier.datamart.mapper.SearchReportsFileMapper;
import com.haier.datamart.service.ISearchReportsFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 报表指标间表服务实现类
 * @author zuoqb123
 * @date 2018-10-09
 */
@Service
@Transactional
public class SearchReportsFileServiceImpl extends ServiceImpl<SearchReportsFileMapper, SearchReportsFile> implements ISearchReportsFileService {

    @Autowired
    private SearchReportsFileMapper searchReportsFileMapper;
   
}
