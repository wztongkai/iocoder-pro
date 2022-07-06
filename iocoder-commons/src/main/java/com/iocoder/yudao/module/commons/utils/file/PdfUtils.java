package com.iocoder.yudao.module.commons.utils.file;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Pdf 相关工具类
 *
 * @author wu kai
 * @date 2022/7/6
 */
@Slf4j
public class PdfUtils {

    /**
     * PDF转图片
     *
     * @param file PDF文件的二进制流
     */
    public static List<Map<String, Object>> toImage(byte[] file) {
        List<Map<String, Object>> result = new LinkedList<>();
        try (PDDocument document = PDDocument.load(file)) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int i = 0; i < document.getNumberOfPages(); ++i) {
                Map<String, Object> map = new HashMap<>();
                // 索引信息
                map.put("index", i + 1);
                // 图片信息
                BufferedImage bufferedImage = renderer.renderImageWithDPI(i, 100);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", out);
                map.put("image", out.toByteArray());
                result.add(map);
            }
        } catch (Exception e) {
            log.error("PDF转图片异常！", e);
            throw new RuntimeException("PDF转图片异常!");
        }
        return result;
    }

    /**
     * PDF缩略图
     *
     * @param file PDF文件的二进制流
     * @return 缩略图信息
     */
    public static List<Map<String, Object>> toThumbnail(byte[] file) {
        List<Map<String, Object>> result = new LinkedList<>();
        try (PDDocument document = PDDocument.load(file)) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int i = 0; i < document.getNumberOfPages(); ++i) {
                Map<String, Object> map = new HashMap<>();
                // 索引信息
                map.put("index", i + 1);
                // 图片信息
                BufferedImage bufferedImage = renderer.renderImageWithDPI(i, 100);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Thumbnails.of(bufferedImage)
                        .scale(1)
                        .outputQuality(0.5f)
                        .outputFormat("PNG")
                        .toOutputStream(out);
                String thumbnail = "data:image/png;base64," + Base64.getEncoder()
                        .encodeToString(out.toByteArray());
                map.put("thumbnail", thumbnail);
                result.add(map);
            }
        } catch (Exception e) {
            log.error("PDF转缩略图异常！", e);
            throw new RuntimeException("PDF转缩略图异常!");
        }
        return result;
    }

    /**
     * PDF合并
     *
     * @param bytes 合并字节数组
     * @return 合并后的PDF文件字节数组
     */
    public static byte[] mergePdf(List<byte[]> bytes) {
        log.info("===================  PDF合并开始！ ====================");
        try {
            // 创建一个新的PDF
            Document document = new Document();
            // 创建一个字节数组缓冲区
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 创建复制对象
            PdfSmartCopy pdfSmartCopy = new PdfSmartCopy(document, bos);
            // 打开文档操作
            document.open();
            // 文档处理
            for (byte[] bs : bytes) {
                PdfReader reader = new PdfReader(bs);
                int pageTotal = reader.getNumberOfPages();
                for (int pageNo = 1; pageNo <= pageTotal; pageNo++) {
                    document.newPage();
                    PdfImportedPage page = pdfSmartCopy.getImportedPage(reader, pageNo);
                    pdfSmartCopy.addPage(page);
                }
                reader.close();
            }
            // 关闭文档操作
            document.close();
            // 结果返回
            byte[] result = bos.toByteArray();
            bos.close();
            pdfSmartCopy.close();
            return result;
        } catch (Exception e) {
            log.error("PDF合并异常！", e);
            throw new RuntimeException("PDF合并异常!");
        }
    }

    /**
     * PDF重组
     *
     * @param info 重组文件信息
     * @param file 重组文件
     * @return 重组后的PDF文件字节数组
     */
    public static byte[] recombine(Map<Integer, Object> info, Map<String, Object> file) {
        log.info("PDF合并开始！");
        try {
            // 创建一个新的PDF
            Document document = new Document();
            // 创建一个字节数组缓冲区
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 创建复制对象
            PdfSmartCopy pdfSmartCopy = new PdfSmartCopy(document, bos);
            // 打开文档操作
            document.open();
            // 文档处理
            Set<Integer> set = info.keySet();
            Iterator<Integer> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map<String, String> fileInfo = (Map<String, String>) info.get(iterator.next());
                // PDF文件信息
                String fileId = fileInfo.get("file");
                byte[] pdf = (byte[]) file.get(fileId);
                PdfReader reader = new PdfReader(pdf);
                // 文件复制
                document.newPage();
                PdfImportedPage page = pdfSmartCopy.getImportedPage(reader, Integer.parseInt(fileInfo.get("index")));
                pdfSmartCopy.addPage(page);

                reader.close();
            }
            // 关闭文档操作
            document.close();
            // 结果返回
            byte[] result = bos.toByteArray();
            bos.close();
            pdfSmartCopy.close();
            return result;
        } catch (Exception e) {
            log.error("PDF合并异常！", e);
            throw new RuntimeException("PDF合并异常!");
        }
    }


    /**
     * HTML 字符串转化为 PDF文件
     *
     * @param htmlStr HTML字符串
     * @param target  生成后PDF的文件地址  （路径 + 文件名）
     */
    public static void htmlStrToPdf(String htmlStr, String target) throws Exception {
        Document document = new Document(PageSize.A4);
        OutputStream outputStream = new FileOutputStream(new File(target));
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
//        org.jsoup.nodes.Document contentDoc = Jsoup.parseBodyFragment(htmlStr);
//        org.jsoup.nodes.Document.OutputSettings outputSettings = new org.jsoup.nodes.Document.OutputSettings();
//        outputSettings.syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
//        contentDoc.outputSettings(outputSettings);
//        String parsedHtml = contentDoc.outerHtml();
        //这儿的font-family不支持汉字，{font-family:仿宋} 是不可以的。
//        InputStream cssIs = new ByteArrayInputStream("* {font-family: fangsong;}".getBytes(StandardCharsets.UTF_8));
        //第四个参数是html中的css文件的输入流
        //第五个参数是字体提供者，使用系统默认支持的字体时，可以不传。
        XMLWorkerHelper.getInstance()
                .parseXHtml(writer, document, new ByteArrayInputStream(htmlStr.getBytes()), (Charset) null, new MyFontProvider());

        //关闭
        document.close();
    }

    /**
     * HTML文件转PDF
     *
     * @param src    html路径
     * @param target pdf路径
     */
    public static void htmlFileToPdf(String src, String target) throws Exception {
        InputStream htmlFileStream = new FileInputStream(src);
        OutputStream pdfFileStream = new FileOutputStream(target);
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        document.setMargins(30, 30, 30, 30);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfFileStream);
        pdfWriter.setViewerPreferences(PdfWriter.HideToolbar);
        document.open();
        XMLWorkerHelper.getInstance()
                .parseXHtml(pdfWriter, document, htmlFileStream, (Charset) null, new MyFontProvider());
        document.close();
        htmlFileStream.close();
        pdfFileStream.close();
    }

    /**
     *
     * 给pdf 添加图片水印
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
            // 读取pdf文档信息
            Map<String, Object> pdfMsg = getPdfMsg(inSource);
            //获取文件宽度
            float startAddress = Float.parseFloat(pdfMsg.get("width").toString());
            PdfReader reader = new PdfReader(new FileInputStream(inSource));
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outSource));

            //利用关键字查找文件中的内容
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
            if (CollectionUtils.isEmpty(arrLists)) {
                //默认盖章位置
                img.setAbsolutePosition(startAddress - 188, 70);
                PdfContentByte overContent = stamper.getOverContent(pageNum);
                overContent.addImage(img);
                stamper.close();
                reader.close();
                return;
            }
//            if (!arrLists.get(0).isEmpty()) {
//                // 关键字所在的页码数
//                if (Objects.equals(keyWord, "单位领导签字")) {
//                    img.setAbsolutePosition(arrLists.get(0).get(0)[0] - 370,
//                            arrLists.get(0).get(0)[1] - 70);
//                }
//                PdfContentByte under = stamper.getOverContent(toAddPageNum);
//                under.addImage(img);
//                //添加文字水印
//                //doTextHide(under);
//                //图片水印
//                //doImgHide(under, sealPNG);
//            }
            PdfContentByte under = stamper.getOverContent(pageNum);
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

            stamper.close();
            reader.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * pdf 添加文字水印
     * @param under
     * @param startAddress
     * @param x
     * @param y
     * @param date
     * @param fontSize
     * @throws DocumentException
     * @throws IOException
     */
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
        under.endText();
    }

    /**
     * 获取pdf文档信息
     *
     * @param pdfPath pdf 文件路径
     * @return 文档信息
     */
    public static Map<String, Object> getPdfMsg(String pdfPath) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            // 获取PDF共有几页
            PdfReader pdfReader = new PdfReader(new FileInputStream(pdfPath));
            int pages = pdfReader.getNumberOfPages();
            map.put("pageSize", pages);
            // 获取PDF 的宽高
            PdfReader pdfreader = new PdfReader(pdfPath);
            Document document = new Document(pdfreader.getPageSize(pages));
            float widths = document.getPageSize().getWidth();
            // 获取页面高度
            float heights = document.getPageSize().getHeight();
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
     * 获取关键字位置(找第一个，其他的忽略)
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
                    public String pdfsb = "";
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
            log.error("出现异常" + e.getMessage());
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


}

class MyFontProvider extends XMLWorkerFontProvider {

    @Override
    public Font getFont(final String fontname, final String encoding, final
    boolean embedded, final float size, final int style, final BaseColor color) {

        BaseFont bf = null;
        try {
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Font font = new Font(bf, size, style, color);
        font.setColor(color);
        return font;
    }
}
