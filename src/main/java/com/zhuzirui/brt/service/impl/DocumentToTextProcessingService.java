package com.zhuzirui.brt.service.impl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DocumentToTextProcessingService implements com.zhuzirui.brt.service.DocumentToTextProcessingService {

    private static final Logger logger = Logger.getLogger(DocumentToTextProcessingService.class.getName());

    //pdf转换成纯文本
    protected String convertPdfToText(File pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(document.getNumberOfPages());
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    //docx转换成纯文本
    protected String convertDocxToText(File textFile) throws IOException {
        String text = "";
        FileInputStream fis = new FileInputStream(textFile);
        XWPFDocument document = new XWPFDocument(fis);
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            text = text + para.getText() + "\n";
        }
        document.close();
        fis.close();
        return text;
    }

    //txt转换成纯文本
    protected String convertTxtToText(String textFile) throws IOException {
        String text = "";
        BufferedReader br = new BufferedReader(new FileReader(textFile));
        String line = "";
        while ((line = br.readLine()) != null) {
            text = text + line + "\n";
        }
        br.close();
        return text;
    }

    @Override
    public String convertToText(File fileContent) {
        try {
            switch (fileContent.getName().substring(fileContent.getName().lastIndexOf(".") + 1)) {
                case "pdf":
                    String text = convertPdfToText(fileContent);
                    System.out.println(text);
                    return text;
                case "doc":
                case "docx":
                    String text2 = convertDocxToText(fileContent);
                    System.out.println(text2);
                    return text2;
                case "txt":
                    String text3 = convertTxtToText(fileContent.getAbsolutePath());
                    System.out.println(text3);
                    return text3;
                default:
                    throw new IllegalArgumentException("Unsupported file type: " + fileContent.getName());
            }
        }catch (IOException e){
            logger.warning("Error while converting file to text: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args){
        DocumentToTextProcessingService service = new DocumentToTextProcessingService();
        File pdfFile = new File("D:\\xz\\CCF-CSP必学知识\\template.pdf");
        File docxFile = new File("D:\\xz\\CCF-CSP必学知识\\code_snippets.docx");
        try {
//            String s = service.convertPdfToText(pdfFile);
//            System.out.println(s);
            String text = service.convertPdfToText(pdfFile);
            System.out.println(text);
        }catch (IOException e){
            logger.warning("Error while converting file to text: " + e.getMessage());
        }
    }
}
