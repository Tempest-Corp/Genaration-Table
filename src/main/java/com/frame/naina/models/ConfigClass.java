package com.frame.naina.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;

public class ConfigClass {

    HashMap<String, String> types;

    String package_var;
    String ending;
    String import_var;
    Boolean func_get_set;

    public static ConfigClass getConfigClass(String langage) throws FileNotFoundException {

        String filePath = "./config/config.json"; // default

        FileReader fileReader = new FileReader(filePath);
        JSONTokener tokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(tokener);

        String java = jsonObject.get(langage.toLowerCase()).toString();

        Gson gson = new Gson();
        return gson.fromJson(java, ConfigClass.class);

    }

    public HashMap<String, String> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }

    public String getPackage_var() {
        return package_var;
    }

    public void setPackage_var(String package_var) {
        this.package_var = package_var;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
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

}
