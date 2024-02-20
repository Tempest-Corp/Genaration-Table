package com.frame.naina.Data;

import java.util.ArrayList;
import java.util.List;

import com.frame.naina.func.EntityReader;
import com.frame.naina.models.ClassBuilder;
import com.frame.naina.models.Column;

public class ResponseHandler {

    String templatePath;
    String[] fields;
    String name;
    String context;
    String[] imports;
    String fieldEncapsulation;
    String[] annotationsModule;
    String[] fieldAnnotationModule;

    public ClassBuilder build(EntityReader entityReader) {

        ClassBuilder classBuilder = new ClassBuilder(name);
        entityReader.getLanguage().getModel().setAnnotationsModule(this.annotationsModule);
        entityReader.getLanguage().getModel().setFieldEncapsulation(this.fieldEncapsulation);
        entityReader.getLanguage().getModule().setFieldAnnotationModule(this.fieldAnnotationModule);

        classBuilder.setLanguage(entityReader.getLanguage());
        classBuilder.setPackageName(entityReader.getPackageName());
        classBuilder.setImports(entityReader.arrayToListString(this.getImports()));

        classBuilder.getLanguage().setModule(classBuilder.getLanguage().getModel());
        classBuilder.getLanguage().getModule().setFieldAnnotationModule(this.fieldAnnotationModule);
        classBuilder.setTemplateFile(this.templatePath);
        classBuilder.setColumns(getColumns(entityReader));
        classBuilder.build(entityReader.getPathFile(), this.context);
        return classBuilder;
    }

    public List<Column> getColumns(EntityReader entityReader) {
        List<Column> result = new ArrayList<Column>();
        entityReader.getLanguage().getModule().setAnnotationsModule(this.annotationsModule);
        for (int i = 0; i < fields.length; i++) {
            String[] colArray = fields[i].split(" ");
            Column col = entityReader.toColumn(colArray[1], colArray[0], "true", null, null);
            col.setConfigClass(entityReader.getLanguage());
            col.setIsPK(false);
            result.add(col);
        }
        return result;
    }

    public String[] getImports() {
        return imports;
    }

    public void setImports(String[] imports) {
        this.imports = imports;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public void setFieldEncapsulation(String fieldEncapsulation) {
        this.fieldEncapsulation = fieldEncapsulation;
    }

    public void setAnnotationsModule(String[] annotationsModule) {
        this.annotationsModule = annotationsModule;
    }

    public void setFieldAnnotationModule(String[] fieldAnnotationModule) {
        this.fieldAnnotationModule = fieldAnnotationModule;
    }

    public String getFieldEncapsulation() {
        return fieldEncapsulation;
    }

    public String[] getAnnotationsModule() {
        return annotationsModule;
    }

    public String[] getFieldAnnotationModule() {
        return fieldAnnotationModule;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
