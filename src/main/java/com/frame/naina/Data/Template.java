package com.frame.naina.Data;

import java.util.HashMap;

public class Template {

    public static String getTemplateFile(String langage, String filePath) {

        String templatePath = "";

        if (filePath == null) {
            HashMap<String, String> templates = templateDefaultPath();
            templatePath = templates.get(langage);
        } else
            templatePath = filePath;

        return templatePath;
    }

    public static HashMap<String, String> templateDefaultPath() {
        HashMap<String, String> templates = new HashMap<String, String>();

        templates.put("JAVA", "templateClass/Java.temp");
        templates.put("CS", "templateClass/CS.temp");

        return templates;
    }

}
