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
    Model model;
    Controller controller;
    View view;
    Boolean func_get_set;

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

    public Boolean getFunc_get_set() {
        return func_get_set;
    }

    public void setFunc_get_set(Boolean func_get_set) {
        this.func_get_set = func_get_set;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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

}
