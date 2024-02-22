package com.frame.naina.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
                content += row.replace("\\t", "\t").replace("\\n", "\n") + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException("File " + filePath + " not found");
        }
        return content;
    }

    public static void unzip(String filePath, String destDirectory) throws IOException {

        try {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(filePath));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String newfilePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zipIn, newfilePath);
                } else {
                    // if the entry is a directory, create the directory
                    File dir = new File(newfilePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public static void moveFolder(String folderPath, String destinationPath) {
        try {
            Path source = Paths.get(folderPath);
            Path destination = Paths.get(destinationPath);
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolder(String folderPath) {
        cleanFolder(new File(folderPath));
    }

    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists())
            folder.mkdir();
    }

    public static void cleanFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    cleanFolder(file);
                }
            }
        }
        if (!folder.delete()) {
            System.err.println("Failed to delete: " + folder);
        }
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.delete()) {
            System.err.println("Failed to delete: " + filePath);
        }
    }

    public static void writeFile(String file, String content, String pathFiles) {
        try {

            Path filePath = Path.of(pathFiles + file);
            emptyFile(filePath.toAbsolutePath().toString());

            Files.writeString(filePath, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void emptyFile(String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void createFolders(String filePath) {

        String[] folderPart = filePath.split("/");
        String fileName = "";
        for (String partFileName : folderPart) {
            fileName += partFileName + "/";
            File folder = new File(fileName);
            if (!folder.exists())
                folder.mkdir();
        }

    }
}
