package com.frame.naina.models;

public class Column {

    String name;

    String type;

    Boolean isNullable;

    String columnDef;

    String langage;

    ConfigClass configClass;

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
        return this.configClass.getTypes().get(this.type);
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

    public String getLangage() {
        return langage;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    public ConfigClass getConfigClass() {
        return configClass;
    }

    public void setConfigClass(ConfigClass configClass) {
        this.configClass = configClass;
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", type=" + type + ", isNullable=" + isNullable + ", columnDef=" + columnDef
                + "]";
    }

}
