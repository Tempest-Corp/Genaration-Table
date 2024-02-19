package com.frame.naina.Data;

import java.io.FileNotFoundException;
import java.util.HashMap;

import com.frame.naina.utils.Transform;
import com.google.gson.Gson;

public class Language {

    String name;
    HashMap<String, String> types;
    HashMap<String, String> packageSyntax;
    String import_var;
    String import_ending;
    Module module;
    ///
    Model model;
    Controller controller;
    View view;
    Respository repository;

    public static Language getLangageConfig(String langage) throws FileNotFoundException {
        String filePath = LandMark.LANGUAGES_DATA;
        Gson gson = new Gson();
        Language[] languages = gson.fromJson(Transform.getContent(filePath), Language[].class);
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].getName().equalsIgnoreCase(langage))
                return languages[i];
        }
        throw new RuntimeException("Language " + langage + " not found");
    }

    public HashMap<String, String> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }

    public HashMap<String, String> getPackageSyntax() {
        return packageSyntax;
    }

    public void setPackageSyntax(HashMap<String, String> packageSyntax) {
        this.packageSyntax = packageSyntax;
    }

    public String getImport_var() {
        return import_var;
    }

    public void setImport_var(String import_var) {
        this.import_var = import_var;
    }

    public Model getModel() {
        return model;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Respository getRepository() {
        return repository;
    }

    public void setRepository(Respository repository) {
        this.repository = repository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getImport_ending() {
        return import_ending;
    }

    public void setImport_ending(String import_ending) {
        this.import_ending = import_ending;
    }

    @Override
    public String toString() {
        return "Language [name=" + name + ", types=" + types + ", packageSyntax=" + packageSyntax + ", import_var="
                + import_var + ", import_ending=" + import_ending + ", model=" + model + ", controller=" + controller
                + ", view=" + view + "]";
    }

}
