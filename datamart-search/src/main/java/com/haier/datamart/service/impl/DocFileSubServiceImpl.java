package com.haier.datamart.service.impl;

import com.haier.datamart.entity.DocFileSub;
import com.haier.datamart.mapper.DocFileSubMapper;
import com.haier.datamart.service.IDocFileSubService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 文件编码表服务实现类
 * @author zuoqb123
 * @date 2018-11-12
 */
@Service
@Transactional
public class DocFileSubServiceImpl extends ServiceImpl<DocFileSubMapper, DocFileSub> implements IDocFileSubService {

    @Autowired
    private DocFileSubMapper docFileSubMapper;
   
}
