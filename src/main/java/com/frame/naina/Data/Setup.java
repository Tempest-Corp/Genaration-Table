package com.frame.naina.Data;

public class Setup {

    String projectName;
    String language;
    Template template;
    Integer databaseConfig;
    Database database;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Integer getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(Integer databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "Setup [projectName=" + projectName + ", language=" + language + ", template=" + template
                + ", databaseConfig=" + databaseConfig + ", database=" + database + "]";
    }

}
