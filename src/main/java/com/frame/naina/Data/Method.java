package com.frame.naina.Data;

import java.io.FileNotFoundException;

import com.frame.naina.models.ClassBuilder;
import com.frame.naina.utils.Transform;

public class Method {

    String[] annotations;
    String templatePath;

    public String getStruture(ClassBuilder classBuilder) throws FileNotFoundException {
        return replaceAll(Transform.getContent(templatePath), classBuilder);
    }

    public String getAnnotationsList(ClassBuilder classBuilder) {
        String annotations = "";
        for (String annotation : this.annotations) {
            annotations += "\t" + annotation + "\n";
        }
        annotations = replaceAll(annotations, classBuilder);
        return annotations;
    }

    public String replaceAll(String line, ClassBuilder classBuilder) {
        // line = line.replace("(TypeTemplate)",classBuilder.getTypeTemplate());
        // line = line.replace("(fieldNameMaj)", toCamelCase(this.name));
        // line = line.replace("(fieldName)", this.name);
        return line;
    }

    public String[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String[] annotations) {
        this.annotations = annotations;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
