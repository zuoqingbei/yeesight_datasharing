package com.haier.datamart.service.impl;

import com.haier.datamart.entity.DataInterfaceTableRelative;
import com.haier.datamart.mapper.DataInterfaceTableRelativeMapper;
import com.haier.datamart.service.IDataInterfaceTableRelativeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 统一接口中解析的sql中表关系服务实现类
 * @author zuoqb123
 * @date 2018-12-07
 */
@Service
@Transactional
public class DataInterfaceTableRelativeServiceImpl extends ServiceImpl<DataInterfaceTableRelativeMapper, DataInterfaceTableRelative> implements IDataInterfaceTableRelativeService {

    @Autowired
    private DataInterfaceTableRelativeMapper dataInterfaceTableRelativeMapper;
   
}
