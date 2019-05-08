package com.enterise.web.htmlgen.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
 

import org.aspectj.weaver.ast.Test;
 

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
 
 
/**
 * @author Administrator
 * @version $Id$
 * @since
 * @see
 */
public class Word2PdfUtil {
 
    public static void main(String[] args) {
        //doc2pdf("C:/Users/lss/Desktop/test.doc");
    	String inPath="D://kubi//upload//20181113//360006F45-备份恢复演练报告-20131201(20181113123551).docx";
		String outPath = "D://kubi//upload//20181113//12.pdf";
    	doc2pdf(inPath, outPath);
    }
 
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Test.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
    public static boolean doc2pdf(String inPath, String outPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }
        System.out.println(inPath);
        System.out.println(outPath);
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath); // 新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
                                         // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
            os.close();
            doc.clearSectionAttrs();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
