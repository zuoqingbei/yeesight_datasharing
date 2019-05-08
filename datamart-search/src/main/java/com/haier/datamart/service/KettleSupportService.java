package com.haier.datamart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by long on 2018/11/12.
 */
@Service
public class KettleSupportService {
    @Value("airflowsupport.service.KettleSupportService.filePath")
    private String filePath;
    private static  final String SIMPLE_KETTLE_TEMPLATE="airflow_support/kettle_template/simple_template";



}
