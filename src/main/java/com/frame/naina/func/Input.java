package com.frame.naina.func;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.ResourceUtils;

import com.frame.naina.Data.Database;
import com.frame.naina.Data.LandMark;
import com.frame.naina.Data.Language;
import com.frame.naina.Data.Setup;
import com.frame.naina.utils.Transform;

public class Input {

    String databaseName;
    String databaseUsername;
    String password;
    String packageName;
    String langage;
    String pathFile = "./";
    String modelTemplate;
    List<String> imports = new ArrayList<String>();
    Database database;
    Language language;
    Setup setup;
    //
    Database[] allDatabases;
    Language[] allLanguages;

    public void ask() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        getConfigData();
        if (!useDefaultValue(reader)) {
            askDatabases(reader);
            askLangage(reader);
            askPathFile(reader);
            askPackageName(reader);
            askImports(reader);
        }
    }

    public void getConfigData() {
        try {
            /// SETUP
            this.setup = Transform.fromJson(Setup.class,
                    Transform.getContent(LandMark.SETUP_DATA));

            this.modelTemplate = this.setup.getTemplate().getModel();
            /// DATABASES
            this.allDatabases = Transform.fromJson(Database[].class,
                    Transform.getContent(LandMark.DATABASES_DATA));
            /// LANGUAGES
            this.allLanguages = Transform.fromJson(Language[].class,
                    Transform.getContent(LandMark.LANGUAGES_DATA));
            getDefaults();

            this.langage = this.language.getName().toUpperCase();
            this.packageName = this.language.getPackageSyntax().get("name");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getDefaults() {

        for (Database database : this.allDatabases) {
            if (this.setup.getDatabaseConfig().equals(database.getId())) {
                this.database = database;
                this.setup.setDatabase(database);
            }

        }
        for (Language language : this.allLanguages) {
            if (this.setup.getLanguage().equals(language.getName()))
                this.language = language;
        }
        this.databaseName = this.database.getDatabaseName();
        this.databaseUsername = this.database.getUsername();
        this.password = this.database.getPassword();

        for (String importDependance : this.language.getModel().getImports()) {
            this.imports.add(importDependance);
        }

    }

    public Boolean useDefaultValue(BufferedReader bufferedReader) throws IOException {
        System.out.print("Use the default value from config files ? : (Y/n)  ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return bufferedReader.readLine().toString().equalsIgnoreCase("Y");
    }

    public void askDatabases(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter your database information : ");

        System.out.print("Name [" + this.database.getDatabaseName() + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String name = bufferedReader.readLine();
        this.databaseName = empty(name) ? this.databaseName : name;
        this.database.setDatabaseName(this.databaseName);

        System.out.print("Username [" + this.database.getUsername() + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String username = bufferedReader.readLine();
        this.databaseUsername = empty(username) ? this.databaseUsername : username;
        this.database.setUsername(this.databaseUsername);

        handlePassword();
    }

    public void handlePassword() {
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword("password [" + this.getDatabase().getPassword() + "] : ");
            String password = new String(passwordArray);
            this.password = empty(password) ? this.password : password;
            this.database.setPassword(this.password);

            java.util.Arrays.fill(passwordArray, ' ');
        }
        System.out.println("");
    }

    public void askLangage(BufferedReader bufferedReader) throws IOException {
        Boolean correct = false;
        while (!correct) {
            System.out.println("What do you want to use : ");
            for (int i = 1; i < allLanguages.length + 1; i++) {
                System.out.println(i + ") " + this.allLanguages[i - 1].getName() + (i == 1 ? " [default]" : ""));
            }
            System.out.print("=> ");
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String langage = bufferedReader.readLine();
            if (empty(langage)) {
                this.langage = allLanguages[0].getName().toUpperCase();
                correct = true;
            } else {
                langage = langage.toLowerCase().trim();
                langage = getLangageEquivalence(Integer.valueOf(langage));
                if (langage != null) {
                    this.langage = langage;
                    correct = true;
                    break;
                } else
                    System.out.println("=> Invalid choice\n");
            }
        }

        System.out.println("");
    }

    public void askPathFile(BufferedReader bufferedReader) throws IOException {

        System.out.println("Where to generate the files [" + this.pathFile + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String pathFile = bufferedReader.readLine();
        this.pathFile = empty(pathFile) ? this.pathFile : pathFile;
        System.out.println("");
    }

    public void askPackageName(BufferedReader bufferedReader) throws IOException {

        System.out.println("Package name [" + this.language.getPackageSyntax().get("name") + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String packagename = bufferedReader.readLine();
        this.packageName = empty(packagename) ? this.packageName : packagename;
        System.out.println();
    }

    public void askImports(BufferedReader bufferedReader) throws IOException {

        Boolean correct = false;
        System.out.println("Imports (optional): ");
        while (!correct) {
            System.out.print("=> ");
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String importName = bufferedReader.readLine();
            if (empty(importName))
                correct = true;
            else {
                this.imports.add(importName);
            }
        }
        System.out.println("");
    }

    public String getLangageEquivalence(int choiceIndex) {
        HashMap<Integer, String> typeMap = new HashMap<Integer, String>();

        int index = 1;
        for (Language language : allLanguages) {
            typeMap.put(index, language.getName().toUpperCase());
            index++;
        }

        String equivalence = typeMap.get(choiceIndex);// nampitoboko ho manoboka 1 mantsy

        return equivalence;
    }

    public Boolean isFileTemplateExist(String filePath) throws FileNotFoundException {
        File fileInRessource = ResourceUtils.getFile("./" + filePath);

        return fileInRessource.exists() ? true : false;
    }

    public Boolean empty(String text) {
        if (text.trim().equals("") || text == null)
            return true;
        else
            return false;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getModelTemplate() {
        return modelTemplate;
    }

    public void setModelTemplate(String modelTemplate) {
        this.modelTemplate = modelTemplate;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public Database[] getAllDatabases() {
        return allDatabases;
    }

    public void setAllDatabases(Database[] allDatabases) {
        this.allDatabases = allDatabases;
    }

    public Language[] getAllLanguages() {
        return allLanguages;
    }

    public void setAllLanguages(Language[] allLanguages) {
        this.allLanguages = allLanguages;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLangage() {
        return langage;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Input [databaseName=" + databaseName + ", databaseUsername=" + databaseUsername + ", password="
                + password + ", packageName=" + packageName + ", langage=" + langage + ", pathFile=" + pathFile + "]";
    }

}
