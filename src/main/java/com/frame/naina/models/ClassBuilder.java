package com.frame.naina.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Vector;

import org.springframework.util.ResourceUtils;

import com.frame.naina.Data.Language;

public class ClassBuilder {

    String className;

    String tableName;

    List<Column> columns = new Vector<Column>();

    String packageName;

    List<String> imports = new Vector<String>();

    String templateFile;

    Language language;

    String context; // model,controller,repository,view

    public ClassBuilder(String className) {
        this.className = className;
        this.tableName = className;
    }

    public void build(String filePath, String context) {

        toCamelCaseClassName();
        addContext(context);
        String contentFile = buildContentFile(readFile(this.templateFile));
        String file = this.className + "." + this.language.getName().toLowerCase();
        writeFile(file,
                contentFile, filePath);
        removeContext(context);
    }

    public void addContext(String context) {
        this.context = context;
        this.packageName += "." + context;
        addExtensionClassName();
    }

    public void removeContext(String context) {
        this.context = "";
        this.packageName = this.packageName.replace("." + context, "");
        removeExtensionClassName();
    }

    public void writeFile(String file, String content, String pathFiles) {
        try {
            pathFiles = !pathFiles.startsWith("../") ? pathFiles.replaceFirst("./", "") : pathFiles;

            String pathPackageName = pathFiles + packageToPath();
            createFolder(pathPackageName);

            Path filePath = Path.of(pathPackageName + "/" + file);
            emptyFile(filePath.toAbsolutePath().toString());

            Files.writeString(filePath, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emptyFile(String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.close(); // This will truncate the file
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public String packageToPath() {
        return this.packageName.replaceAll("\\.", "/");
    }

    public void createFolder(String filePath) {

        String[] folderPart = filePath.split("/");
        String fileName = "";
        for (String partFileName : folderPart) {
            fileName += partFileName + "/";
            File folder = new File(fileName);
            if (!folder.exists())
                folder.mkdir();
        }

    }

    public List<String> readFile(String file) {
        //
        List<String> lines = new Vector<String>();
        try {

            @SuppressWarnings("null")
            File fileInRessource = ResourceUtils.getFile(file);
            Path filePath = Path.of(fileInRessource.getPath());
            // // Read all lines from the file
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public String buildContentFile(List<String> linesTemplate) {
        String content = "";
        for (String line : linesTemplate) {
            content += inspectLine(line);
        }
        content += "\n";
        content = replaceAll(content);
        return content;
    }

    public void addExtensionClassName() {
        this.className = this.className + this.language.getModule().getClassNameExtension();
    }

    public void removeExtensionClassName() {
        this.className = this.className.replace(this.language.getModule().getClassNameExtension(), "");
    }

    public String inspectLine(String line) {
        String line_inspected = "";
        line_inspected += switchWord(line);
        return line_inspected;
    }

    public String switchWord(String line) {
        line = jumpDown(attributs(
                setters(getters(constructor(ClassName(imports((packageName(line)))))))));
        line = classAnnotations(line);
        return line;
    }

    public String classAnnotations(String line) {
        String classAnnotations = "";
        if (line.contains("(classAnnotations)")) {
            int index = 0;
            for (String annotation : this.language.getModule().getAnnotationsModule()) {
                classAnnotations += annotation
                        + (index != this.language.getModule().getAnnotationsModule().length - 1 ? "\n" : "\n");
                index++;
            }
        }
        line = line.replace("(classAnnotations)", classAnnotations);
        return line;
    }

    public String setters(String line) {
        String setters = "";

        if (line.contains("(setters)")) {
            for (Column column : columns) {
                setters += column.setters() + "\n";
            }
        }
        line = line.replace("(setters)", setters);
        return line;
    }

    public String getters(String line) {
        String getters = "";

        if (line.contains("(getters)")) {
            for (Column column : columns) {
                getters += column.getters() + "\n";
            }
        }
        line = line.replace("(getters)", getters);
        return line;
    }

    public String imports(String line) {
        String importsLines = "";
        if (line.contains("(imports)")) {
            for (String importRow : this.imports) {
                importsLines += this.language.getImport_var() + " " + importRow
                        + this.language.getImport_ending()
                        + "\n";
            }
        }
        line = line.replace("(imports)", "\n" + importsLines + "\n");
        return line;
    }

    public String attributs(String line) {
        String importsLines = "";
        if (line.contains("(attributs)")) {
            for (Column column : this.columns) {
                if (column.isPK)
                    importsLines += column.getPkAnnotation();
                else if (column.isFK)
                    importsLines += column.getFkAnnotation();
                importsLines += "\t" + column.getTypeTemplate() + " " + column.getName() + getFieldEnding(line)
                        + "\n\n";
            }
            line = line.replace("(attributs)", importsLines);
        }

        return line;
    }

    public String getFieldEnding(String line) {
        return this.language.getModule().getFieldEnding();
    }

    public String packageName(String line) {
        String package_tag = "(package_name)";
        String package_var = "(package_var)";
        // String package_start = "(package_start)";
        // String package_end = "(package_end)";
        //
        String extendss = "(extends)";

        String[] parts = line.split(" ");
        for (String part : parts) {
            if (part.trim().equals(package_tag)) {
                line = line.replace(package_tag, this.packageName);
            } else if (part.trim().equals(package_var)) {
                line = line.replace(package_var, this.language.getPackageSyntax().get("variable")) + " ";
            }
        }
        line = line.replace(extendss, this.language.getModule().getExtendsModule());
        return line;
    }

    public String ClassName(String line) {
        String package_tag = "(class_name)";
        if (line.contains(package_tag)) {
            return line.replace(package_tag, this.className);
        }
        return line;
    }

    public String jumpDown(String line) {
        if (line.contains("\\n"))
            return line.replace("\\n", "\n");
        else if (line.trim().equals(""))
            return ("\n");
        else
            return line;
    }

    public void toCamelCaseClassName() {
        this.className = toCamelCase(this.className);
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

    public String constructor(String line) {
        String constLines = "";
        if (line.contains("(constructor)")) {
            constLines += language.getModule().getConstructor();
            constLines = constLines.replace("(ClassName)", getClassName());
            constLines = constLines.replace("(constructorParams)", constructorParams());
            constLines = constLines.replace("(constructorInner)", constructorInner());
            line = line.replace("(constructor)", constLines);
        }

        return line;
    }

    public String constructorParams() {
        String params = "";
        int index = 0;
        for (Column column : columns) {
            params += column.getTypeTemplate() + " " + column.getName() + (index != columns.size() - 1 ? ", " : "");
            index++;
        }
        return params;
    }

    public String constructorInner() {
        String params = "";
        for (Column column : columns) {
            params += "\t\tthis." + column.getName() + " = " + column.getName() + ";\n";
        }
        return params;
    }

    public String replaceAll(String content) {
        String package_tag = "(package_name)";
        String package_start = "(package_start)";
        String package_end = "(package_end)";
        String tableName = "(tableName)";
        String extendss = "(extends)";
        String className = "(ClassName)";
        String class_name = "(class_name)";

        content = content.replace(package_tag, this.packageName);
        content = content.replace(package_start, this.language.getPackageSyntax().get("start").toString());
        content = content.replace(package_end, this.language.getPackageSyntax().get("end").toString());
        content = content.replace(extendss, this.language.getModule().getExtendsModule());
        content = content.replace(className,
                this.className.replace(this.language.getModule().getClassNameExtension(), ""));
        content = content.replace(class_name,
                this.className.replace(this.language.getModule().getClassNameExtension(), ""));
        content = content.replace(tableName, this.tableName);
        return content;
    }

    public void setlanguage(Language language) {
        this.language = language;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "ClassBuilder [className=" + className + ", columns=" + columns +
                ", packageName=" + packageName + ", imports=" + imports + ", templateFile=" + templateFile + "]";
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

}
