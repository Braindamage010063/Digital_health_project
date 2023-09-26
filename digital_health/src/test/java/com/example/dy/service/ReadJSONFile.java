package com.example.dy.service;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJSONFile {
    public static void main(String[] args) {
        // 指定JSON文件的路径
        String filePath = "comp3820-digital-health-be5b27c74b2d.json";

        try {
            // 读取文件内容为字符串
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            // 将JSON字符串解析为JSONObject或JSONArray
            // 如果您知道JSON文件的根是一个对象，则使用JSONObject，否则使用JSONArray
            JSONObject jsonObject = new JSONObject(jsonContent);
            // 或者，如果是JSON数组，可以使用以下方式
            // JSONArray jsonArray = new JSONArray(jsonContent);

            // 输出JSON内容
            System.out.println("JSON内容：");
            System.out.println(jsonObject.toString(4)); // 使用缩进格式输出JSON内容，4表示缩进空格数

            // 如果需要访问JSON对象的特定字段，可以使用如下方式：
            // String value = jsonObject.getString("key");

            // 如果需要访问JSON数组的元素，可以使用如下方式：
            // String element = jsonArray.getString(index);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

