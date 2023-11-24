package com.frame.naina.models;

import java.util.HashMap;

public class Column {

    String name;

    String type;

    Boolean isNullable;

    String columnDef;

    String langage;

    public Column(String name, String type, Boolean isNullable, String columnDef, String langage) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.columnDef = columnDef;
        this.langage = langage;
    }

    public String getters() {
        return "\tpublic " + getTypeTemplate() + " get" + toCamelCase(this.name) + "() {\n" +
                "\t\treturn " + "this." + this.name + ";\n" +
                "\t}\n";
    }

    public String setters() {
        return "\tpublic void set" + toCamelCase(this.name) +
                "(" + getTypeTemplate() + " " + this.name + ") " +
                "{\n" +
                "\t\tthis." + this.name + " = " + this.name + ";\n" +
                "\t}\n";
    }

    public String getTypeTemplate() {
        this.type = this.type.toUpperCase();
        return getEquivalenceType(this.langage + "_" + this.type);
    }

    public String toCamelCase(String text) {
        String textCamelCase = "";
        if (text.length() > 0 && text != null) {
            Boolean upperCase = true;
            for (char letter : text.toCharArray()) {
                if (isNotCorrectCharacter(letter)) {
                    upperCase = true;
                    continue;
                }
                textCamelCase += upperCase ? Character.toUpperCase(letter) : letter;
                upperCase = false;
            }
        }
        return textCamelCase;
    }

    public Boolean isNotCorrectCharacter(char character) {
        if (character == '_')
            return true;
        else
            return false;
    }

    public static String getEquivalenceType(String type) {
        HashMap<String, String> typeMap = new HashMap<String, String>();

        // JAVA
        typeMap.put("JAVA_VARCHAR", "String");
        typeMap.put("JAVA_NUMERIC", "Double");
        typeMap.put("JAVA_SERIAL", "Integer");
        typeMap.put("JAVA_BOOL", "Boolean");
        typeMap.put("JAVA_TIMESTAMP", "String");
        typeMap.put("JAVA_INT4", "Integer");

        // C#
        typeMap.put("CS_VARCHAR", "string");
        typeMap.put("CS_NUMERIC", "double");
        typeMap.put("CS_SERIAL", "int");
        typeMap.put("CS_BOOL", "Boolean");
        typeMap.put("CS_TIMESTAMP", "string");
        typeMap.put("CS_INT4", "int");

        String equivalence = typeMap.get(type);

        return equivalence != null ? equivalence : "String";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(Boolean isNullable) {
        this.isNullable = isNullable;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", type=" + type + ", isNullable=" + isNullable + ", columnDef=" + columnDef
                + "]";
    }

}
