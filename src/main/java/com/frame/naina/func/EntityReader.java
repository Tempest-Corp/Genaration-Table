package com.frame.naina.func;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.frame.naina.connection.Connect;
import com.frame.naina.models.ClassBuilder;
import com.frame.naina.models.Column;
import com.frame.naina.models.ConfigClass;

public class EntityReader {

    private String database;

    private String username;

    private String password;

    private String packageName;

    private String pathFile;

    private String langage;

    private List<String> imports = new ArrayList<String>();

    private String templateFile;

    public EntityReader(String database, String username, String password, Input input) {
        this.database = database;
        this.username = username;
        this.password = password;
        addInputData(input);
    }

    public void readTables() throws SQLException, FileNotFoundException {
        Connection connect = Connect.getConnectionPostgresql(database, username, password);
        List<ClassBuilder> classes = getAllClassesSchemaBuilder(connect);

        for (ClassBuilder classBuilder : classes) {
            classBuilder.setTemplateFile(templateFile);
            classBuilder.setPackageName(this.packageName);
            classBuilder.setTemplate(this.langage);
            classBuilder.setImports(imports);
            classBuilder.setLangage(this.langage);
            classBuilder.build(this.pathFile);
        }

        connect.close();
    }

    public List<ClassBuilder> getAllClassesSchemaBuilder(Connection connection)
            throws SQLException, FileNotFoundException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, "%", new String[] {
                "TABLE" });
        List<ClassBuilder> classes = new Vector<ClassBuilder>();
        ConfigClass configClass = ConfigClass.getConfigClass(this.langage.toLowerCase());

        while (resultSet.next()) {

            String tableName = resultSet.getString("TABLE_NAME");
            ResultSet resCol = metaData.getColumns(null, null, tableName, null);
            ClassBuilder classBuilder = new ClassBuilder(tableName);
            classBuilder.setConfigClass(configClass);

            while (resCol.next()) {
                String columnName = resCol.getString("COLUMN_NAME");
                String columnType = resCol.getString("TYPE_NAME");
                String isNullable = resCol.getString("IS_NULLABLE");
                String columnDefinition = resCol.getString("COLUMN_DEF");

                Column col = toColumn(columnName, columnType, isNullable, columnDefinition);
                col.setConfigClass(configClass);
                classBuilder.getColumns().add(col);
            }
            classes.add(classBuilder);
        }
        return classes;
    }

    public Column toColumn(String columnName, String typeName, String isNullable, String definition) {
        Boolean is_nullable = Boolean.parseBoolean(isNullable);
        return new Column(columnName, typeName, is_nullable, definition, this.langage);
    }

    public void showColumnResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMD = resultSet.getMetaData();
        int columnCount = resultSetMD.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMD.getColumnName(i);
            System.out.println("Column " + i + ": " + columnName);
        }
    }

    public void addInputData(Input input) {
        this.setPackageName(input.getPackageName());
        this.setPathFile(input.getPathFile());
        this.setTemplateFile(input.getTemplateFile());
        this.setLangage(input.getLangage());
        this.setImports(input.getImports());
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getLangage() {
        return langage;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

}
