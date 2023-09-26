package com.example.dy.service;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;

public class CloudStorageExample {

    public static void main(String[] args) throws IOException {
        // 加载 JSON 文件作为 Google Credentials
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                CloudStorageExample.class.getResourceAsStream("/ocr_test/comp3820-digital-health-be5b27c74b2d.json"));
        // 在这里你可以使用 storage 对象进行 Google Cloud Storage 操作
        // 例如 storage.list()、storage.create() 等等
    }
}

