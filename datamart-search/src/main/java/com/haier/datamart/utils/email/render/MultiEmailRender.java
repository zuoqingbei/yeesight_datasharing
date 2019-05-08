package com.haier.datamart.utils.email.render;

import com.haier.datamart.entity.MailForQueryBeanParent;
import com.haier.datamart.utils.email.render.bean.BeanTransferUtil;
import com.haier.datamart.utils.email.render.bean.MultiEmailBean;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 通过html模板渲染邮件内容
 */
@Component
public class MultiEmailRender {
    private static TemplateEngine TEMPLATE_ENGIN;
    @Resource
    public void setTemplateEngine(TemplateEngine templateEngine){
        TEMPLATE_ENGIN = templateEngine;
    }

    public static String renderEmail(MailForQueryBeanParent entity,String templatePath){
        Context context = new Context();
        if (entity != null) {
            MultiEmailBean mailEntry = BeanTransferUtil.tranOrigToBean(entity);
            context.setVariable("mailEntry",mailEntry);
        }
        return  TEMPLATE_ENGIN.process(templatePath, context);
    }
}
