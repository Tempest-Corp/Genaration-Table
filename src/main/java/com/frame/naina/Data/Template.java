package com.frame.naina.Data;

public class Template {

    public String model, controller, view;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "Template [model=" + model + ", controller=" + controller + ", view=" + view + "]";
    }

}
