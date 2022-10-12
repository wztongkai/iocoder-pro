package com.iocoder.yudao.module.commons.utils.generate;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.utils.io.FileUtils;
import com.spire.doc.*;
import com.spire.doc.documents.BookmarksNavigator;
import com.spire.doc.documents.DocumentObjectType;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextBodyPart;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * word 操作工具类  使用 poi-tl 根据模板生成word
 *
 * @author wu kai
 * @since 2022/10/12
 */
@Slf4j
public class WordUtils {

    // 生成的word文件后缀
    public static final String GENERATE_WORD_SUFFIX = ".docx";

    /**
     * 根据模板填充内容生成word
     *
     * @param templatePath 模板地址
     * @param wordFilePath 生成后word存放路径
     * @param wordFileName 生成后的word文件名
     * @param dataMap      填充word内容的参数
     * @param dataList     word模板中表格数据集合在 Map中的 key
     * @return 生成后word文件地址
     */
    public static String generateWordByTemplate(String templatePath, String wordFilePath, String wordFileName, Map<String, Object> dataMap, String dataList) {
        // 参数校验
        Assertion.isBlank(templatePath, "模板文件地址不能为空");
        Assertion.isBlank(wordFilePath, "word存放地址不能为空");
        Assertion.isBlank(wordFileName, "生成的word名称不能为空");
        Assertion.isNull(dataMap, "替换word内容的数据不能为空");
        // 拼接文件名（带文件后缀）
        wordFileName = wordFileName + GENERATE_WORD_SUFFIX;
        // 生成的文件的存放路径
        if (!wordFilePath.endsWith("/")) {
            wordFilePath = wordFilePath + File.separator;
        }
        // 存放目录是否存在，不存在则创建
        FileUtils.createFileDir(wordFilePath);
        // 拼接生成后的word路径
        String filePath = wordFilePath + wordFileName;
        // 读取模板，并将dataMap中的数据填充进模板文件中
        try {
            URL url = new URL(templatePath);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            LoopRowTableRenderPolicy loopRowTableRenderPolicy = new LoopRowTableRenderPolicy();
            Configure configure = Configure.builder().bind(dataList, loopRowTableRenderPolicy).build();
            XWPFTemplate xwpfTemplate = XWPFTemplate.compile(bis, configure).render(dataMap);
            xwpfTemplate.writeToFile(filePath);
            xwpfTemplate.close();
            bis.close();
        } catch (IOException e) {
            log.error("生成word--->{} 异常，异常信息为：{}", wordFileName, e.getMessage());
            e.printStackTrace();
        }
        return filePath;
    }


    /**
     * word 文件合并
     *
     * @param wordPathList         多个word文件链接的集合
     * @param outMergeWordFilePath 合并后的word地址（全路径）
     */
    public static void mergeWord(List<String> wordPathList, String outMergeWordFilePath) {
        Assertion.isNull(wordPathList, "文件链接不能为空");
        List<OPCPackage> opcPackagesList = new ArrayList<>();
        try {
            for (String wordPath : wordPathList) {
                OPCPackage scrPackage = OPCPackage.open(wordPath);
                opcPackagesList.add(scrPackage);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outMergeWordFilePath);
            XWPFDocument xwpfDocument = new XWPFDocument(opcPackagesList.get(0));
            CTBody body = xwpfDocument.getDocument().getBody();
            // 设置分页
            XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
            xwpfParagraph.setPageBreak(true);
            if (opcPackagesList.size() > 1) {
                for (int i = 1; i < opcPackagesList.size(); i++) {
                    XWPFDocument xwpfDocument1 = new XWPFDocument(opcPackagesList.get(i));
                    CTBody body1 = xwpfDocument1.getDocument().getBody();
                    // 分页
                    XWPFParagraph xwpfParagraph1 = xwpfDocument1.createParagraph();
                    xwpfParagraph1.setPageBreak(true);
                    List<XWPFPictureData> allPictures = xwpfDocument1.getAllPictures();
                    // 记录图片合并前及合并后的ID
                    HashMap<String, String> map = new HashMap<>();
                    for (XWPFPictureData picture : allPictures) {
                        String before = xwpfDocument1.getRelationId(picture);
                        //将原文档中的图片加入到目标文档中
                        String after = xwpfDocument.addPictureData(picture.getData(), Document.PICTURE_TYPE_JPEG);
                        map.put(before, after);
                    }
                    // 拼接
                    appendBody(body,body1,map);
                }
            }
            xwpfDocument.write(fileOutputStream);
        } catch (InvalidFormatException | IOException | XmlException e) {
            log.error("word 合并异常，异常信息为：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 拼接
     * @param src word内容
     * @param append 需要拼接的word内容
     * @param map word中的图片
     */
    private static void appendBody(CTBody src, CTBody append, Map<String, String> map) throws XmlException {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        if (map != null && !map.isEmpty()) {
            ListIterator<Map.Entry<String, String>> i = new ArrayList<>(map.entrySet()).listIterator(map.size());
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
            while (i.hasPrevious()) {
                Map.Entry<String, String> entry = i.previous();
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
                addPart = addPart.replace("<a:blip r:embed=\"" + entry.getKey(), "<a:blip r:embed=\"" + entry.getValue());
            }
        }
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
        src.set(makeBody);
    }

    /**
     * 通过书签名给word 插入图片
     * @param srcPath 原文件地址
     * @param imagePath 图片地址
     * @param bookMarkName 书签名称
     */
    public static void wordInsImageByBookMarkName(String srcPath, String imagePath, String bookMarkName){
        com.spire.doc.Document doc = new com.spire.doc.Document();
        doc.loadFromFile(srcPath);
        // 获取文件长宽、坐标、文字环绕方式等信息
        DocPicture wordImageInfo = getWordImageInfo(srcPath);
        //获取文档中的指定段落
        BookmarksNavigator bookmarksNavigator = new BookmarksNavigator(doc);
        bookmarksNavigator.moveToBookmark(bookMarkName);
        //替换段落中的第一张图片
        Paragraph para= new Paragraph(doc);
        DocPicture picture = para.appendPicture(imagePath);
        picture.setWidth(wordImageInfo.getWidth());
        picture.setHeight(wordImageInfo.getHeight());
        // 设置文字环绕方式
        picture.setTextWrappingStyle(wordImageInfo.getTextWrappingStyle());
        picture.setTextWrappingType(wordImageInfo.getTextWrappingType());
        // 设置图片坐标 - X
        picture.setHorizontalPosition(wordImageInfo.getHorizontalPosition());
        // 设置图片坐标 - Y
        picture.setVerticalPosition(wordImageInfo.getVerticalPosition());
        TextBodyPart bodyPart = new TextBodyPart(doc);
        bodyPart.getBodyItems().add(para);
        bookmarksNavigator.replaceBookmarkContent(bodyPart); //保存文档
        //保存结果文档
        doc.saveToFile(srcPath, FileFormat.Docx_2013);
        doc.dispose();
    }

    /**
     * 获取word中的图片信息
     * @param srcPath word路径
     * @return 图片信息
     */
    public static DocPicture getWordImageInfo(String srcPath){
        //加载Word测试文档
        com.spire.doc.Document doc = new com.spire.doc.Document();
        doc.loadFromFile(srcPath);

        //遍历section
        for (int a = 0; a < doc.getSections().getCount(); a++) {
            Section section = doc.getSections().get(a);

            //遍历paragraph段落
            for (int b = 0; b < section.getParagraphs().getCount(); b++) {
                Paragraph paragraph = section.getParagraphs().get(b);

                //遍历段落中的对象
                for (int i = 0; i < paragraph.getChildObjects().getCount(); i++) {
                    DocumentObject docobj = paragraph.getChildObjects().get(i);

                    //判断对象是否为图片
                    if (docobj.getDocumentObjectType() == DocumentObjectType.Picture) {
                        return (DocPicture) docobj;
                    }
                }
            }
        }
        return null;
    }

    public static void operateWord(int[] copyRows, List<int[]> insertRows, String srcPath, List<String> xuList){
        com.spire.doc.Document doc = new com.spire.doc.Document(srcPath);
        Section section = doc.getSections().get(1);
        Table table = section.getTables().get(0);

        int s = 0;
        for (int[] row : insertRows) {
            // 复制行，并插入指定行中
            TableRow cloneRow1 = table.getRows().get(copyRows[0]).deepClone();
            TableRow cloneRow2 = table.getRows().get(copyRows[1]).deepClone();
            TableRow cloneRow3 = table.getRows().get(copyRows[2]).deepClone();
            table.getRows().insert(row[0], cloneRow1);
            table.getRows().insert(row[1], cloneRow2);
            table.getRows().insert(row[2], cloneRow3);

            // 添加（续）
            TableRow xuRow = table.getRows().get(row[0]);
            for (int i = 0; i < xuRow.getCells().getCount(); i++) {
                TableCell cell = xuRow.getCells().get(i);
                //遍历单元格中的段落
                for (int k = 0; k < cell.getParagraphs().getCount(); k++)
                {
                    Paragraph paragraph = cell.getParagraphs().get(k);
                    String text = paragraph.getText();
                    if(StringUtils.isNotBlank(text)){
                        TextRange textRange = paragraph.appendText(xuList.get(s));
                        textRange.getCharacterFormat().setFontName("宋体");
                        textRange.getCharacterFormat().setBold(true);
                        textRange.getCharacterFormat().setItalic(false);
                        textRange.getCharacterFormat().setFontSize(16);
                        s++;
                    }
                }
            }
            // 删除行内容
            TableRow deleteRow = table.getRows().get(row[1]);
            for (int i = 0; i < deleteRow.getCells().getCount(); i++) {
                //获取单元格
                TableCell cell = deleteRow.getCells().get(i);
                cell.getChildObjects().clear();
            }
            deleteRow.setHeight(20f);
        }
        doc.saveToFile(srcPath, FileFormat.Docx_2013);
        doc.dispose();

    }
}
