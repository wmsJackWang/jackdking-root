//package view.parse;
//
//import com.aspose.pdf.Document;
//import com.aspose.pdf.SaveFormat;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
///**
// * Copyright (C) 阿里巴巴
// *
// * @ClassName Pdf2Word
// * @Description TODO
// * @Author jackdking
// * @Date 30/06/2022 3:26 下午
// * @Version 2.0
// **/
//public class PdfWordConverter {
//    public static void main(String[] args) throws IOException {
//        pdfToDoc("/Users/jackdking/github/jackdking-root/jackdking-gongtushe/jackdking-pdf-word-cvt/file/Payday lenders- Heroes or villains.pdf");
//    }
//
//    public static void pdfToDoc(String pdfPath) {
//        long old = System.currentTimeMillis();
//        try {
//            String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
//            FileOutputStream os = new FileOutputStream(wordPath);
//            Document doc = new Document(pdfPath);
//            doc.save(os, SaveFormat.DocX);
//
//            os.close();
//            long now = System.currentTimeMillis();
//            System.out.println("pdf转word文件耗时：" + ((now - old) / 1000.0) + "秒");
//        } catch (Exception e) {
//            System.out.println("pdf转word文件失败！");
//            e.printStackTrace();
//        }
//    }
//}
