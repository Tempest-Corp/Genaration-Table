package com.frame.naina.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.GsonBuilder;

public class Transform {

    public static <T> T fromJson(Class<T> classTarget, String json) {
        GsonBuilder builder = new GsonBuilder();
        T object = builder.create().fromJson(json, classTarget);
        return object;
    }

    public static String getJsonContent(String filePath, String key) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filePath);
        JSONTokener tokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(tokener);
        return jsonObject.get(key).toString();
    }

    public static String getContent(String filePath) throws FileNotFoundException {
        return getAllFileContent(filePath);
    }

    public static String getAllFileContent(String filePath) {
        String content = "";
        try {
            List<String> list = Files.readAllLines(Paths.get(filePath));
            for (String row : list) {
                content += row;
            }
        } catch (Exception e) {
            throw new RuntimeException("File " + filePath + " not found");
        }
        return content;

    }
}
