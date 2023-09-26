package com.example.dy.service;

import com.google.cloud.documentai.v1beta3.*;
import com.google.protobuf.ByteString;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class OcrSample2{
    public static void processOcrDocument()
            throws IOException, InterruptedException, ExecutionException, TimeoutException {
        //String filePath1 = "/comp3820-digital-health-be5b27c74b2d";
        String projectId = "comp3820-digital-health";
        String location = "us"; // Format is "us" or "eu".
        String processerId = "2218c8d2e803727a";
        String filePath = "/sample.png";
        InputStream page = OcrSample1.class.getResourceAsStream(filePath);
        if (page != null) {
            System.out.println("欧吼，恭喜找到");
        }else {
            System.out.println("完蛋，没找到");
        }

        URL resourceUrl = OcrSample1.class.getClassLoader().getResource("comp3820-digital-health-be5b27c74b2d.json");

        if (resourceUrl != null) {
            try {
                String resourcePath = Paths.get(resourceUrl.toURI()).toString();

                // 使用文件系统路径访问文件
                BufferedReader br = new BufferedReader(new FileReader(resourcePath));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line);
                }

                String jsonContent = content.toString();
                System.out.println("JSON 文件内容:");
                System.out.println(jsonContent);

                System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "/comp3820-digital-health-be5b27c74b2d.json");
                String credentialsFilePath = System.getProperty("GOOGLE_APPLICATION_CREDENTIALS");

                if (credentialsFilePath != null) {
                    try (BufferedReader br1 = new BufferedReader(new FileReader(credentialsFilePath))) {
                        StringBuilder content1 = new StringBuilder();
                        String line1;
                        while ((line1 = br1.readLine()) != null) {
                            content1.append(line1);
                        }

                        String jsonContent1 = content1.toString();
                        System.out.println("JSON 凭据文件内容:");
                        System.out.println(jsonContent1);

                        // 在这里可以继续执行其他操作，例如将 JSON 内容解析为凭据对象
                        // ...
                        System.out.println("已设置 GOOGLE_APPLICATION_CREDENTIALS 环境变量，文件路径为：" + credentialsFilePath);

                        // 在这里可以继续执行与 Google Cloud 的操作
                        // ...

                        // TODO(developer): Replace these variables before running the sample.

                        processOcrDocument(projectId, location, processerId, filePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("GOOGLE_APPLICATION_CREDENTIALS 环境变量未设置。");
                }




                // 在这里可以继续执行其他操作，例如将 JSON 内容解析为凭据对象
                // ...

                br.close(); // 关闭文件读取器
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("找不到指定的资源文件。");
        }
    }












    public static void processOcrDocument(
            String projectId, String location, String processorId, String filePath)
            throws IOException, InterruptedException, ExecutionException, TimeoutException {
        // Initialize client that will be used to send requests. This client only needs
        // to be created
        // once, and can be reused for multiple requests. After completing all of your
        // requests, call
        // the "close" method on the client to safely clean up any remaining background
        // resources.
        String endpoint = String.format("%s-documentai.googleapis.com:443", location);
        DocumentProcessorServiceSettings settings =
                DocumentProcessorServiceSettings.newBuilder().setEndpoint(endpoint).build();
        try (DocumentProcessorServiceClient client = DocumentProcessorServiceClient.create(settings)) {
            // The full resource name of the processor, e.g.:
            // projects/project-id/locations/location/processor/processor-id
            // You must create new processors in the Cloud Console first
            String name =
                    String.format("projects/%s/locations/%s/processors/%s", projectId, location, processorId);

            // Read the file.
            byte[] imageFileData = Files.readAllBytes(Paths.get(filePath));

            // Convert the image data to a Buffer and base64 encode it.
            ByteString content = ByteString.copyFrom(imageFileData);

            RawDocument document =
                    RawDocument.newBuilder().setContent(content).setMimeType("application/pdf").build();

            // Configure the process request.
            ProcessRequest request =
                    ProcessRequest.newBuilder().setName(name).setRawDocument(document).build();

            // Recognizes text entities in the PDF document
            ProcessResponse result = client.processDocument(request);
            Document documentResponse = result.getDocument();

            System.out.println("Document processing complete.");

            // Read the text recognition output from the processor
            // For a full list of Document object attributes,
            // please reference this page:
            // https://googleapis.dev/java/google-cloud-document-ai/latest/index.html

            // Get all of the document text as one big string
            String text = documentResponse.getText();
            System.out.printf("Full document text: '%s'\n", escapeNewlines(text));

            // Read the text recognition output from the processor
            List<Document.Page> pages = documentResponse.getPagesList();
            System.out.printf("There are %s page(s) in this document.\n", pages.size());

            for (Document.Page page : pages) {
                System.out.printf("Page %d:\n", page.getPageNumber());
                printPageDimensions(page.getDimension());
                printDetectedLanguages(page.getDetectedLanguagesList());
                printParagraphs(page.getParagraphsList(), text);
                printBlocks(page.getBlocksList(), text);
                printLines(page.getLinesList(), text);
                printTokens(page.getTokensList(), text);
            }
        }
    }

    private static void printPageDimensions(Document.Page.Dimension dimension) {
        String unit = dimension.getUnit();
        System.out.printf("    Width: %.1f %s\n", dimension.getWidth(), unit);
        System.out.printf("    Height: %.1f %s\n", dimension.getHeight(), unit);
    }

    private static void printDetectedLanguages(
            List<Document.Page.DetectedLanguage> detectedLangauges) {
        System.out.println("    Detected languages:");
        for (Document.Page.DetectedLanguage detectedLanguage : detectedLangauges) {
            String languageCode = detectedLanguage.getLanguageCode();
            float confidence = detectedLanguage.getConfidence();
            System.out.printf("        %s (%.2f%%)\n", languageCode, confidence * 100.0);
        }
    }

    private static void printParagraphs(List<Document.Page.Paragraph> paragraphs, String text) {
        System.out.printf("    %d paragraphs detected:\n", paragraphs.size());
        Document.Page.Paragraph firstParagraph = paragraphs.get(0);
        String firstParagraphText = getLayoutText(firstParagraph.getLayout().getTextAnchor(), text);
        System.out.printf("        First paragraph text: %s\n", escapeNewlines(firstParagraphText));
        Document.Page.Paragraph lastParagraph = paragraphs.get(paragraphs.size() - 1);
        String lastParagraphText = getLayoutText(lastParagraph.getLayout().getTextAnchor(), text);
        System.out.printf("        Last paragraph text: %s\n", escapeNewlines(lastParagraphText));
    }

    private static void printBlocks(List<Document.Page.Block> blocks, String text) {
        System.out.printf("    %d blocks detected:\n", blocks.size());
        Document.Page.Block firstBlock = blocks.get(0);
        String firstBlockText = getLayoutText(firstBlock.getLayout().getTextAnchor(), text);
        System.out.printf("        First block text: %s\n", escapeNewlines(firstBlockText));
        Document.Page.Block lastBlock = blocks.get(blocks.size() - 1);
        String lastBlockText = getLayoutText(lastBlock.getLayout().getTextAnchor(), text);
        System.out.printf("        Last block text: %s\n", escapeNewlines(lastBlockText));
    }

    private static void printLines(List<Document.Page.Line> lines, String text) {
        System.out.printf("    %d lines detected:\n", lines.size());
        Document.Page.Line firstLine = lines.get(0);
        String firstLineText = getLayoutText(firstLine.getLayout().getTextAnchor(), text);
        System.out.printf("        First line text: %s\n", escapeNewlines(firstLineText));
        Document.Page.Line lastLine = lines.get(lines.size() - 1);
        String lastLineText = getLayoutText(lastLine.getLayout().getTextAnchor(), text);
        System.out.printf("        Last line text: %s\n", escapeNewlines(lastLineText));
    }

    private static void printTokens(List<Document.Page.Token> tokens, String text) {
        System.out.printf("    %d tokens detected:\n", tokens.size());
        Document.Page.Token firstToken = tokens.get(0);
        String firstTokenText = getLayoutText(firstToken.getLayout().getTextAnchor(), text);
        System.out.printf("        First token text: %s\n", escapeNewlines(firstTokenText));
        Document.Page.Token lastToken = tokens.get(tokens.size() - 1);
        String lastTokenText = getLayoutText(lastToken.getLayout().getTextAnchor(), text);
        System.out.printf("        Last token text: %s\n", escapeNewlines(lastTokenText));
    }

    // Extract shards from the text field
    private static String getLayoutText(Document.TextAnchor textAnchor, String text) {
        if (textAnchor.getTextSegmentsList().size() > 0) {
            int startIdx = (int) textAnchor.getTextSegments(0).getStartIndex();
            int endIdx = (int) textAnchor.getTextSegments(0).getEndIndex();
            return text.substring(startIdx, endIdx);
        }
        return "[NO TEXT]";
    }

    public static void main(String[] args) {
        // 设置 GOOGLE_APPLICATION_CREDENTIALS 环境变量
        String keyFilePath = "/resources/ocr_test/comp3820-digital-health-be5b27c74b2d.json";
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", keyFilePath);

        // 测试输出环境变量的值
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        System.out.println("GOOGLE_APPLICATION_CREDENTIALS: " + credentialsPath);
        // 在这里继续你的代码逻辑...
    }

    private static String escapeNewlines(String s) {
        return s.replace("\n", "\\n").replace("\r", "\\r");
    }
}




