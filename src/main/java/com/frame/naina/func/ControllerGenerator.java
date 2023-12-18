package com.frame.naina.func;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.frame.naina.models.ClassBuilder;

public class ControllerGenerator {
    String extension = "java";
    ClassBuilder classBuilder = new ClassBuilder();

    public void generateController(String outputPath, String fileName, String packageName,
            String controllerName, String pathTemplate, String framework) {

        try {
            // Lire le contenu du modèle Java
            Path templatePath = Paths.get(pathTemplate);
            String templateContent = new String(Files.readAllBytes(templatePath));

            // Remplacer les balises [[packageName]] et [[controllerName]]
            templateContent = templateContent.replace("[[packageName]]", packageName);
            templateContent = templateContent.replace("[[controllerName]]", controllerName);

            // Écrire le fichier généré
            if (framework.equalsIgnoreCase("SPRING_BOOT_CONTROLLER")) {
                this.extension = "java";
            } else {
                this.extension = "cs";
            }
            String packagePath = createPackage(outputPath, packageName);

            String filePath = packagePath + "/" + fileName + "." + this.extension;
            Files.write(Paths.get(filePath), templateContent.getBytes());

            System.out.println("Controller generated successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createPackage(String dataOutputPath, String dataPackage) {
        String packagePart = dataOutputPath + dataPackage.replace(".", "/");
        classBuilder.createFolder(packagePart);
        return packagePart;
    }
}
