package com.frame.naina.models;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Vector;

import org.springframework.util.ResourceUtils;

public class ClassBuilder {

    String className;

    List<Column> columns = new Vector<Column>();

    String template = "JAVA";

    String packageName = "com.frame.naina.Result";

    List<String> imports = new Vector<String>();

    String templateFile;

    // Mila importation automatic raha ohatra ka mila anleh izy Timestamp ohatra ,
    // java.sql.Timestamp;

    public ClassBuilder(String className) {
        this.className = className;
    }

    public ClassBuilder() {
    }

    public void build(String filePath) {
        toCamelCaseClassName();
        String contentFile = buildContentFile(readFile(this.templateFile));
        String file = this.className + "." + this.template.toLowerCase();
        writeFile(file,
                contentFile, filePath);
    }

    public void writeFile(String file, String content, String pathFiles) {
        try {
            pathFiles = !pathFiles.startsWith("../") ? pathFiles.replaceFirst("./", "") : pathFiles;

            String pathPackageName = pathFiles + packageToPath();
            createFolder(pathPackageName);

            Path filePath = Path.of(pathPackageName + "/" + file);

            Files.writeString(filePath, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
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
        return content;
    }

    public String inspectExtensionClassName(String line) {
        if (line.contains("$ext_cn=")) {
            String[] parts = line.split("=");
            this.className = this.className + parts[1];
            line = "\r";
        }
        return line;
    }

    public String inspectLine(String line) {
        String line_inspected = "";
        line_inspected += switchWord(line);
        return line_inspected;
    }

    public String switchWord(String line) {
        line = jumpDown(attributs(
                setters(getters(constructor(ClassName(imports(inspectExtensionClassName(packageName(line)))))))));
        return line;
    }

    public String setters(String line) {
        String setters = "";
        if (line.contains("[[setters")) {
            for (Column column : columns) {
                setters += column.setters() + "\n";
            }
        }
        line = line.replace("[[setters", setters);
        return line;
    }

    public String getters(String line) {
        String getters = "";
        if (line.contains("[[getters")) {
            for (Column column : columns) {
                getters += column.getters() + "\n";
            }
        }
        line = line.replace("[[getters", getters);
        return line;
    }

    public String imports(String line) {
        String importsLines = "";
        if (line.contains("[[name=imports")) {
            for (String importRow : this.imports) {
                if (this.template.equals("JAVA"))
                    importsLines += "import " + importRow + ";\n";
                else if (this.template.equals("CS"))
                    importsLines += "using " + importRow + ";\n";
            }
        }
        line = line.replace("[[name=imports", "\n" + importsLines + "\n");
        return line;
    }

    public String attributs(String line) {
        String importsLines = "";
        if (line.contains("[[name=attributs")) {
            for (Column column : this.columns) {
                importsLines += "\t " + column.getTypeTemplate() + " " + column.getName() + getSet(line) + "\n";
            }
            line = line.replace("[[name=attributs", importsLines);
            line = line.replace(":get:set", "");
        }

        return line;
    }

    public String getSet(String line) {
        if (line.contains(":get:set")) {
            return " { get; set; } ";
        } else
            return ";";
    }

    public String packageName(String line) {
        String package_tag = "%package__name%";
        if (line.contains(package_tag)) {
            return line.replace(package_tag, this.packageName) + ";";
        }
        return line;
    }

    public String ClassName(String line) {
        String package_tag = "%class_name%";
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
        if (line.contains("[[name=constructor")) {

            constLines += "\tpublic " + getClassName() + "(" + constructorParams() + "){\n"
                    + constructorInner() +
                    "\t}";
            line = line.replace("[[name=constructor", constLines);
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    @Override
    public String toString() {
        return "ClassBuilder [className=" + className + ", columns=" + columns + ", template=" + template
                + ", packageName=" + packageName + ", imports=" + imports + ", templateFile=" + templateFile + "]";
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

}
