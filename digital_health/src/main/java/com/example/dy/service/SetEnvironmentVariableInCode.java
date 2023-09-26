package com.example.dy.service;

public class SetEnvironmentVariableInCode {
    public static void main(String[] args) {
        // 设置 GOOGLE_APPLICATION_CREDENTIALS 环境变量
        String keyFilePath = "/resources/ocr_test/comp3820-digital-health-be5b27c74b2d.json";
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", keyFilePath);

        // 测试输出环境变量的值
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        System.out.println("GOOGLE_APPLICATION_CREDENTIALS: " + credentialsPath);
        // 在这里继续你的代码逻辑...
    }
}

