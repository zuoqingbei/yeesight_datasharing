package com.haier.datamart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.SearchReports2;
import com.haier.datamart.mapper.SearchReportsMapper;
import com.haier.datamart.mapper.SearchReportsMapper2;
import com.haier.datamart.service.ISearchReportsService2;
/**
 * 报表信息服务实现类
 * @author zuoqb123
 * @date 2018-09-29
 */
@Service
@Transactional
public class SearchReportsServiceImpl2 extends ServiceImpl<SearchReportsMapper2, SearchReports2> implements ISearchReportsService2 {

    @Autowired
    private SearchReportsMapper searchReportsMapper;
   
}
