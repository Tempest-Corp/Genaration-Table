package com.frame.naina.Data;

public class Module {

    String classNameExtension;
    String[] imports;
    String[] annotationsModule;
    String extendsModule;
    String fieldEnding;
    String constructor;
    String getters;
    String setters;
    String[] pkAnnotationModule;
    String pkType;
    String[] fkAnnotationModule;

    public String[] getImports() {
        return imports;
    }

    public void setImports(String[] imports) {
        this.imports = imports;
    }

    public String[] getAnnotationsModule() {
        return annotationsModule;
    }

    public void setAnnotationsModule(String[] annotationsModule) {
        this.annotationsModule = annotationsModule;
    }

    public String getExtendsModule() {
        return extendsModule;
    }

    public void setExtendsModule(String extendsModule) {
        this.extendsModule = extendsModule;
    }

    public String getFieldEnding() {
        return fieldEnding;
    }

    public void setFieldEnding(String fieldEnding) {
        this.fieldEnding = fieldEnding;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public String getGetters() {
        return getters;
    }

    public void setGetters(String getters) {
        this.getters = getters;
    }

    public String getSetters() {
        return setters;
    }

    public void setSetters(String setters) {
        this.setters = setters;
    }

    public String[] getPkAnnotationModule() {
        return pkAnnotationModule;
    }

    public void setPkAnnotationModule(String[] pkAnnotationModule) {
        this.pkAnnotationModule = pkAnnotationModule;
    }

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String[] getFkAnnotationModule() {
        return fkAnnotationModule;
    }

    public void setFkAnnotationModule(String[] fkAnnotationModule) {
        this.fkAnnotationModule = fkAnnotationModule;
    }

    public String getClassNameExtension() {
        return classNameExtension;
    }

    public void setClassNameExtension(String classNameExtension) {
        this.classNameExtension = classNameExtension;
    }

}
