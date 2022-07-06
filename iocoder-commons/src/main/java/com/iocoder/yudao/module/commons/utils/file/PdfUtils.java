package com.iocoder.yudao.module.commons.utils.file;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
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
