package com.iocoder.yudao.module.commons.utils.file;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author wu kai
 * @date 2022/4/25
 */
@Slf4j
public class PdfInsertImageUtils {
    /**
     * 给pdf 添加图片
     *
     * @param inSource  pdf 路径
     * @param outSource 生成的pdf文件路径
     * @param sealPNG   需要插入pdf的图片
     */
    public static void pdfAddImages(String inSource, String outSource, String sealPNG, String keyWord) {
        try {
            //读取需要签章的文档信息
            Map<String, Object> pdfMsg = getPdfMsg(inSource);
            //获取文件宽度，默认pdf
            float startAddress = Float.parseFloat(pdfMsg.get("width").toString());
            PdfReader reader = new PdfReader(new FileInputStream(inSource));
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outSource));

            //利用关键字查找文件中的内容，确定签章的位置
            List<String> keyWordsList = new ArrayList<String>();
            keyWordsList.add(keyWord);
            //查找关键字位置
            List<List<float[]>> arrLists = findKeywords(keyWordsList, reader);
            int pageNum = Integer.parseInt(pdfMsg.get("pageSize").toString());
            //插入水印
            Image img = Image.getInstance(sealPNG);
            //设置尺寸
            img.scaleAbsolute(70, 70);// 直接设定显示尺寸
            img.setBackgroundColor(new BaseColor(0));
            //如果没有找到关键字，走默认位置
            if (arrLists.isEmpty()) {
                //默认盖章位置
                img.setAbsolutePosition(startAddress - 188, 70);
                PdfContentByte overContent = stamper.getOverContent(pageNum);
                overContent.addImage(img);
                overContent.beginText();
                //设置字体颜色
                overContent.setColorFill(BaseColor.RED);
                overContent.setTextMatrix(startAddress - 120, 70);
                overContent.showText(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()));
                //设置文字的位置
                overContent.endText();
                stamper.close();
                reader.close();
                return;
            }
            for (int i = pageNum; i > 0; i--) {
                if (!arrLists.get(0).isEmpty()) {
                    // 关键字所在的页码数
                    float pageFlag = arrLists.get(0).get(0)[2];
                    // 当且仅当落款和关键字在同一页时考虑同时盖章
                    if (pageNum == pageFlag && pageFlag == i) {
                        if (Objects.equals(keyWord, "负责人签字")) {
                            img.setAbsolutePosition(arrLists.get(0).get(0)[0] - 200,
                                    arrLists.get(0).get(0)[1]);
                        } else {
                            img.setAbsolutePosition(arrLists.get(0).get(0)[0] - 100,
                                    arrLists.get(0).get(0)[1]);
                        }

                    }
                    if (Objects.equals(keyWord, "负责人签字")) {
                        PdfContentByte under = stamper.getOverContent(i);
                        under.addImage(img);
                        int yearX = 440;
                        int yearY = 273;
                        int monthX = 407;
                        int monthY = 273;
                        int dayX = 388;
                        int dayY = 273;
                        String year = DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
                        String month = DateTimeFormatter.ofPattern("MM").format(LocalDate.now());
                        String day = DateTimeFormatter.ofPattern("dd").format(LocalDate.now());
                        //添加文字水印
                        doTextHide2(under, startAddress, yearX, yearY, year);
                        doTextHide2(under, startAddress, monthX, monthY, month);
                        doTextHide2(under, startAddress, dayX, dayY, day);
                    } else {
                        PdfContentByte under = stamper.getOverContent(i);
                        under.addImage(img);
                        int yearX = 260;
                        int yearY = 273;
                        int monthX = 227;
                        int monthY = 273;
                        int dayX = 208;
                        int dayY = 273;
                        String year = DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
                        String month = DateTimeFormatter.ofPattern("MM").format(LocalDate.now());
                        String day = DateTimeFormatter.ofPattern("dd").format(LocalDate.now());
                        //添加文字水印
                        doTextHide2(under, startAddress, yearX, yearY, year);
                        doTextHide2(under, startAddress, monthX, monthY, month);
                        doTextHide2(under, startAddress, dayX, dayY, day);
                    }

                    //图片水印
//                    doImgHide(under, sealPNG);
                }
            }
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            List<String> strings = new ArrayList<>();
            strings.add(inSource);
            strings.add(sealPNG);

            for (String string : strings) {
                File file = new File(string);
                file.delete();
            }
        }
    }

    public static void doTextHide(PdfContentByte under) throws DocumentException, IOException {
        PdfGState gState = new PdfGState();
        under.beginText();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, BaseFont.WINANSI, BaseFont.NOT_CACHED);
        under.setFontAndSize(bf, 45);
        under.setTextMatrix(30, 30);
        under.setColorFill(BaseColor.PINK);
        gState.setFillOpacity(0.3f);
        under.setGState(gState);
        //设置文字的位置
//        under.showTextAligned(Element.ALIGN_LEFT, "JASON_HSUEH", 230, 430, 45);
        under.endText();
    }

    public static void doTextHide2(PdfContentByte under, float startAddress, int x, int y, String date) throws DocumentException,
            IOException {
        PdfGState gState = new PdfGState();
        under.beginText();
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        under.setFontAndSize(bf, 11);
        under.setTextMatrix(startAddress - x, y);
        gState.setFillOpacity(1f);
        under.setGState(gState);
        //设置字体颜色
        under.setColorFill(BaseColor.BLACK);
        under.showText(date);
        //设置文字的位置
//        under.showTextAligned(Element.ALIGN_LEFT, "JASON_HSUEH", 230, 430, 45);
        under.endText();
    }

    public static void doTextHide2(PdfContentByte under, float startAddress, int x, int y, String date, int fontSize) throws DocumentException,
            IOException {
        PdfGState gState = new PdfGState();
        under.beginText();
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        under.setFontAndSize(bf, fontSize);
        under.setTextMatrix(startAddress - x, y);
        gState.setFillOpacity(1f);
        under.setGState(gState);
        //设置字体颜色
        under.setColorFill(BaseColor.BLACK);
        under.showText(date);
        //设置文字的位置
//        under.showTextAligned(Element.ALIGN_LEFT, "JASON_HSUEH", 230, 430, 45);
        under.endText();
    }

    /**
     * 获取pdf文档信息
     *
     * @param filePath pdf 文件路径
     * @return 获取到的文档信息
     */
    public static Map<String, Object> getPdfMsg(String filePath) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            // 获取PDF共有几页
            PdfReader pdfReader = new PdfReader(new FileInputStream(filePath));
            int pages = pdfReader.getNumberOfPages();
            // System.err.println(pages);
            map.put("pageSize", pages);

            // 获取PDF 的宽高
            PdfReader pdfreader = new PdfReader(filePath);
            Document document = new Document(pdfreader.getPageSize(pages));
            float widths = document.getPageSize().getWidth();
            // 获取页面高度
            float heights = document.getPageSize().getHeight();
            // System.out.println("widths = " + widths + ", heights = " + heights);
            map.put("width", widths);
            map.put("height", heights);
            document.close();
            pdfreader.close();
            pdfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * PDF 添加水印
     *
     * @param under
     * @param path
     * @throws DocumentException
     * @throws IOException
     */
    public static void doImgHide(PdfContentByte under, String path) throws DocumentException, IOException {
        under.beginText();
        PdfGState gState = new PdfGState();
        gState.setStrokeOpacity(0.4f);
        Image image = Image.getInstance(path);
        // 设置坐标 绝对位置 X Y
        image.setAbsolutePosition(200, 300);
        // 设置旋转弧度
        image.setRotation(30);// 旋转 弧度
        // 设置旋转角度
        image.setRotationDegrees(45);// 旋转 角度
        // 设置等比缩放
        image.scalePercent(90);// 依照比例缩放
        // image.scaleAbsolute(200,100);//自定义大小
        // 设置透明度
        under.addImage(image);
        under.setGState(gState);
        under.endText();
        under.stroke();
    }

    /**
     * 获取关键字位置
     *
     * @param keyWords  关键字
     * @param pdfReader pdfReader
     * @return 关键字位置
     */
    public static List<List<float[]>> findKeywords(List<String> keyWords, PdfReader pdfReader) {
        if (keyWords.isEmpty()) {
            return null;
        }
        int pageNum = pdfReader.getNumberOfPages();
        final List<List<float[]>> arrayLists = new ArrayList<List<float[]>>(keyWords.size());
        for (int k = 0; k < keyWords.size(); k++) {
            List<float[]> positions = new ArrayList<float[]>();
            arrayLists.add(positions);
        }
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
        try {
            for (int i = 1; i <= pageNum; i++) {
                final int finalI = i;
                pdfReaderContentParser.processContent(i, new RenderListener() {
                    public String pdfsb = new String();
                    private float yy = -1f;

                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText();
                        com.itextpdf.awt.geom.Rectangle2D.Float boundingRectange = textRenderInfo.getBaseline().getBoundingRectange();
                        if (yy == -1f) {
                            yy = boundingRectange.y;
                        }
                        if (yy != boundingRectange.y) {
                            yy = boundingRectange.y;
                        }
                        pdfsb += text;
                        if (pdfsb.length() > 0) {
                            for (int j = 0; j < keyWords.size(); j++) {
                                List<String> key_words = parseList(keyWords.get(j), ",");
                                //假如配置了多个关键字，找到一个就跑
                                for (final String key_word : key_words) {
                                    if (pdfsb.length() > 0 && pdfsb.toString().contains(key_word)) {
                                        float[] resus = new float[3];
                                        resus[0] = boundingRectange.x + boundingRectange.width * (key_word.length() - 1);
                                        resus[1] = boundingRectange.y;
                                        resus[2] = finalI;
                                        arrayLists.get(j).add(resus);
                                        pdfsb = "";
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                    }

                    @Override
                    public void endTextBlock() {
                    }

                    @Override
                    public void beginTextBlock() {
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("出现异常" + e.getMessage());
        }
        return arrayLists;
    }

    public static List<String> parseList(String source, String regex) {
        if (source.isEmpty()) {
            return null;
        }
        List<String> strList = new ArrayList<String>();
        if (regex.isEmpty()) {
            strList.add(source);
        } else {
            String[] strArr = source.split(regex);
            for (String str : strArr) {
                if (!str.isEmpty()) {
                    strList.add(str);
                }
            }
        }
        return strList;
    }

    /**
     * 将base64字符串转为图片
     */
    public static String base64ToFile(String imgStr, String path, String photoName) {
        String suffix = "";
        if (imgStr.contains("data:image/jpeg;")) {
            suffix = "jpg";
        } else if (imgStr.contains("data:image/png;")) {
            suffix = "png";
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            String baseValue = imgStr.replaceAll(" ", "+");
            byte[] b = decoder.decodeBuffer(baseValue.replace("data:image/jpeg;base64,", "").replace("data:image/png;base64,", ""));
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            File targetFile = new File(path);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            OutputStream out = new FileOutputStream(path + "/" + photoName + "." + suffix);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
        return path + "/" + photoName + "." + suffix;
    }

    /**
     * 给pdf 添加图片
     *
     * @param inSource     pdf路径
     * @param outSource    添加图片后的pdf路径
     * @param sealPNG      图片路径
     * @param keyWord      关键字
     * @param toAddPageNum 要添加图片的页码数
     */
    public static void pdfAddImages(String inSource, String outSource, String sealPNG, String keyWord, int toAddPageNum) {
        try {
            //读取需要签章的文档信息
            Map<String, Object> pdfMsg = getPdfMsg(inSource);
            //获取文件宽度，默认pdf
            float startAddress = Float.parseFloat(pdfMsg.get("width").toString());
            PdfReader reader = new PdfReader(new FileInputStream(inSource));
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outSource));

            //利用关键字查找文件中的内容，确定签章的位置
            List<String> keyWordsList = new ArrayList<String>();
            keyWordsList.add(keyWord);
            //查找关键字位置
            List<List<float[]>> arrLists = findKeywords(keyWordsList, reader);
            int pageNum = Integer.parseInt(pdfMsg.get("pageSize").toString());
            //插入水印
            Image img = Image.getInstance(sealPNG);
            //设置尺寸
            img.scaleAbsolute(100, 50);// 直接设定显示尺寸
            img.setBackgroundColor(new BaseColor(0));
            System.out.println("======================");
            //如果没有找到关键字，走默认位置
            if (arrLists.isEmpty()) {
                System.out.println("000000000");
                //默认盖章位置
                img.setAbsolutePosition(startAddress - 188, 70);
                PdfContentByte overContent = stamper.getOverContent(pageNum);
                overContent.addImage(img);
                overContent.beginText();
                //设置字体颜色
                overContent.setColorFill(BaseColor.RED);
                overContent.setTextMatrix(startAddress - 10, 70);
                overContent.showText(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()));
                //设置文字的位置
                overContent.endText();

                stamper.close();
                reader.close();
                return;
            }
            if (!arrLists.get(0).isEmpty()) {
                // 关键字所在的页码数
                if (Objects.equals(keyWord, "单位领导签字")) {
                    img.setAbsolutePosition(arrLists.get(0).get(0)[0] - 370,
                            arrLists.get(0).get(0)[1] - 70);
                }
                PdfContentByte under = stamper.getOverContent(toAddPageNum);
                under.addImage(img);
                under.beginText();
                //设置字体颜色
                under.setColorFill(BaseColor.RED);
                under.setTextMatrix(startAddress - 10, 70);
                under.showText(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()));
                //设置文字的位置
                under.endText();
                //添加文字水印
                //doTextHide(under);
                //图片水印
                //doImgHide(under, sealPNG);
            }
            if (Objects.equals(keyWord, "单位领导签字")) {
                PdfContentByte under = stamper.getOverContent(toAddPageNum);
                int yearX = 549;
                int yearY = 637;
                int monthX = 503;
                int monthY = 637;
                int dayX = 470;
                int dayY = 637;
                String year = DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
                String month = DateTimeFormatter.ofPattern("MM").format(LocalDate.now());
                String day = DateTimeFormatter.ofPattern("dd").format(LocalDate.now());
                //添加文字水印
                doTextHide2(under, startAddress, yearX, yearY, year,14);
                doTextHide2(under, startAddress, monthX, monthY, month, 14);
                doTextHide2(under, startAddress, dayX, dayY, day, 14);
            } else {
                PdfContentByte under = stamper.getOverContent(pageNum);
                int yearX = 549;
                int yearY = 637;
                int monthX = 503;
                int monthY = 637;
                int dayX = 470;
                int dayY = 637;
                String year = DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
                String month = DateTimeFormatter.ofPattern("MM").format(LocalDate.now());
                String day = DateTimeFormatter.ofPattern("dd").format(LocalDate.now());
                //添加文字水印
                doTextHide2(under, startAddress, yearX, yearY, year,14);
                doTextHide2(under, startAddress, monthX, monthY, month, 14);
                doTextHide2(under, startAddress, dayX, dayY, day, 14);
            }
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 给pdf 添加图片
     * @param inSource     pdf路径
     * @param outSource    添加图片后的pdf路径
     * @param sealPNG      图片路径
     * @param keyWord      关键字
     * @param args         日期坐标
     * args:{
     * int yearX
     * int yearY
     * int monthX
     * int monthY
     * int dayX
     * int dayY
     * }
     */
    public static void pdfAddImages(String inSource, String outSource, String sealPNG, String keyWord, int toAddPageNum, Map<String, Object> args) {
        try {
            //读取需要签章的文档信息
            Map<String, Object> pdfMsg = getPdfMsg(inSource);
            //获取文件宽度，默认pdf
            float startAddress = Float.parseFloat(pdfMsg.get("width").toString());
            PdfReader reader = new PdfReader(new FileInputStream(inSource));
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outSource));

            //利用关键字查找文件中的内容，确定签章的位置
            List<String> keyWordsList = new ArrayList<String>();
            keyWordsList.add(keyWord);
            //查找关键字位置
            List<List<float[]>> arrLists = findKeywords(keyWordsList, reader);
            int pageNum = Integer.parseInt(pdfMsg.get("pageSize").toString());
            //插入水印
            Image img = Image.getInstance(sealPNG);
            //设置尺寸
            img.scaleAbsolute(100, 50);// 直接设定显示尺寸
            img.setBackgroundColor(new BaseColor(0));
            //如果没有找到关键字，走默认位置
            if (arrLists.isEmpty()) {
                //默认盖章位置
                img.setAbsolutePosition(startAddress - 188, 70);
                PdfContentByte overContent = stamper.getOverContent(pageNum);
                overContent.addImage(img);
                stamper.close();
                reader.close();
                return;
            }
            if (!arrLists.get(0).isEmpty()) {
                // 关键字所在的页码数
                if (Objects.equals(keyWord, "单位领导签字")) {
                    img.setAbsolutePosition(arrLists.get(0).get(0)[0] - 370,
                            arrLists.get(0).get(0)[1] - 70);
                }
                PdfContentByte under = stamper.getOverContent(toAddPageNum);
                under.addImage(img);
                //添加文字水印
                //doTextHide(under);
                //图片水印
                //doImgHide(under, sealPNG);
            }
            if (Objects.equals(keyWord, "单位领导签字")) {
                PdfContentByte under = stamper.getOverContent(toAddPageNum);
                //int yearX = 549;
                int yearX = (Integer)args.get("yearX");
                //int yearY = 637;
                int yearY = (Integer)args.get("yearY");
                //int monthX = 503;
                int monthX = (Integer)args.get("monthX");
                //int monthY = 637;
                int monthY = (Integer)args.get("monthY");
                //int dayX = 470;
                int dayX = (Integer)args.get("dayX");
                //int dayY = 637;
                int dayY = (Integer)args.get("dayY");
                String year = DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
                String month = DateTimeFormatter.ofPattern("MM").format(LocalDate.now());
                String day = DateTimeFormatter.ofPattern("dd").format(LocalDate.now());
                //添加文字水印
                doTextHide2(under, startAddress, yearX, yearY, year,14);
                doTextHide2(under, startAddress, monthX, monthY, month, 14);
                doTextHide2(under, startAddress, dayX, dayY, day, 14);
            } else {
                PdfContentByte under = stamper.getOverContent(pageNum);
                int yearX = 549;
                int yearY = 637;
                int monthX = 503;
                int monthY = 637;
                int dayX = 470;
                int dayY = 637;
                String year = DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
                String month = DateTimeFormatter.ofPattern("MM").format(LocalDate.now());
                String day = DateTimeFormatter.ofPattern("dd").format(LocalDate.now());
                //添加文字水印
                doTextHide2(under, startAddress, yearX, yearY, year,14);
                doTextHide2(under, startAddress, monthX, monthY, month, 14);
                doTextHide2(under, startAddress, dayX, dayY, day, 14);
            }
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        PdfInsertImageUtils.pdfAddImages("/Users/wukai/logs/因公临时出国人员备案表.pdf", "/Users/wukai/bbb.pdf", "https://gimg2" +
//                ".baidu.com/image_search/src=http%3A%2F%2Fpic.izihun" +
//                ".com%2Fpic%2Fart_font%2F2019%2F01%2F16%2F10%2F1549221457_2408" +
//                ".jpg%3Fufopmogr3%2Fauto-orient%2Fthumbnail%2F820x%2Fformat%2Fjpeg&refer=http%3A%2F%2Fpic.izihun" +
//                ".com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656746834&t" +
//                "=1e64db5bc3b81b8d3649f1b95040247f", "负责人签字");
//    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("yearX", 556);
        map.put("yearY", 600);
        map.put("monthX", 508);
        map.put("monthY", 600);
        map.put("dayX", 470);
        map.put("dayY", 600);
        PdfInsertImageUtils.pdfAddImages("D:\\data\\image\\FVC-APPLICATION-MATERIALS_1655711845663_来华团组申请表v1.0.pdf", "D:\\data\\image\\GroupData\\test02.pdf", "D:\\data\\image\\电子签名.png","单位领导签字", 1, map);
    }

}
