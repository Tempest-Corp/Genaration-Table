package com.frame.naina.func;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Input {

    String databaseName = "naina_entity";
    String databaseUsername = "postgres";
    String password = "popo";
    String packageName = "com.frame.naina.result";
    String langage = "JAVA";
    String pathFile = "./";
    List<String> imports = new ArrayList<String>();

    public void ask() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        askDatabases(reader);
        askLangage(reader);
        askPathFile(reader);
        askPackageName(reader);
        askImports(reader);
    }

    public void askDatabases(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter your database information : ");

        System.out.print("Name [" + this.databaseName + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String name = bufferedReader.readLine();
        this.databaseName = empty(name) ? this.databaseName : name;

        System.out.print("Username [" + this.databaseUsername + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String username = bufferedReader.readLine();
        this.databaseUsername = empty(username) ? this.databaseUsername : username;

        System.out.print("password [" + this.password + "] : ");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String password = bufferedReader.readLine();
        this.password = empty(password) ? this.password : password;
        System.out.println("");
    }

    public void askLangage(BufferedReader bufferedReader) throws IOException {
        Boolean correct = false;
        while (!correct) {
            System.out.println("What do you want to use : ");
            System.out.println(" a) " + this.langage + " [default]");
            System.out.println(" b) C#");
            System.out.print("=> ");
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String langage = bufferedReader.readLine();
            if (empty(langage))
                correct = true;
            else {
                langage = langage.toLowerCase().trim();
                langage = getLangageEquivalence(langage);
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

        System.out.println("Package name [" + this.packageName + "] : ");
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

    public String getLangageEquivalence(String text) {
        HashMap<String, String> typeMap = new HashMap<String, String>();

        typeMap.put("a", "JAVA");
        typeMap.put("b", "CS");

        String equivalence = typeMap.get(text);

        return equivalence;
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

    @Override
    public String toString() {
        return "Input [databaseName=" + databaseName + ", databaseUsername=" + databaseUsername + ", password="
                + password + ", packageName=" + packageName + ", langage=" + langage + ", pathFile=" + pathFile + "]";
    }

}
