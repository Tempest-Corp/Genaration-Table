package com.frame.naina.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.frame.naina.models.ClassBuilder;
import com.frame.naina.models.Column;
import com.frame.naina.utils.Transform;

public class Form {

    String base_url;

    String addTemplate;

    String deleteTemplate;

    String updateTemplate;

    String dataTemplate;

    HashMap<String, String> types;

    public static void handleView(List<ClassBuilder> classes, Language language, String projectName, String pathFile)
            throws IOException {
        Form form = language.getModule().getForm();

        String rootPath = pathFile + "Front-" + projectName;
        Transform.unzip(LandMark.VIEW_DATA, rootPath);

        // App.jsx
        String appJxs = "App.templ";
        String appFile = Transform.getContent(rootPath + "/src/" + appJxs);
        appFile = handleRouter(classes, appFile);
        Transform.writeFile("App.jsx", appFile, rootPath + "/src/");
        Transform.deleteFile(rootPath + "/src/" + appJxs);

        // NavLink.jsx
        String LinkJxs = "NavLink.templ";
        String linksFile = Transform.getContent(rootPath + "/src/components/Navbar/" + LinkJxs);
        linksFile = handleLinks(classes, linksFile);
        Transform.writeFile("NavLink.jsx", linksFile, rootPath + "/src/components/Navbar/");
        Transform.deleteFile(rootPath + "/src/components/Navbar/" + LinkJxs);

        // Alaivo.js
        String alaivoJxs = "Alaivo.templ";
        String alaivoFile = Transform.getContent(rootPath + "/src/utils/" + alaivoJxs);
        alaivoFile = alaivoFile.replace("(base_url)", "\"" + form.getBase_url() + "\"");
        Transform.writeFile("Alaivo.js", alaivoFile, rootPath + "/src/utils/");
        Transform.deleteFile(rootPath + "/src/utils/" + alaivoJxs);

        // projectName
        String HeadNavJxs = "HeadNav.templ";
        String HeadNavFile = Transform.getContent(rootPath + "/src/components/Navbar/HeadNav/" + HeadNavJxs);
        HeadNavFile = HeadNavFile.replace("(projectName)", projectName);
        Transform.writeFile("HeadNav.jsx", HeadNavFile, rootPath + "/src/components/Navbar/HeadNav/");
        Transform.deleteFile(rootPath + "/src/components/Navbar/HeadNav/" + HeadNavJxs);

        // projectName
        String HomeJxs = "Home.templ";
        String HomeFile = Transform.getContent(rootPath + "/src/components/Home/" + HomeJxs);
        HomeFile = HomeFile.replace("(projectName)", projectName);
        Transform.writeFile("Home.jsx", HomeFile, rootPath + "/src/components/Home/");
        Transform.deleteFile(rootPath + "/src/components/Home/" + HomeJxs);

        String TempJsx = "Temp.templ";

        for (ClassBuilder classBuilder : classes) {
            String content = handleComponent(classBuilder, classes, form, rootPath);
            Transform.writeFile(classBuilder.getClassName() + ".jsx", content, rootPath + "/src/components/Template/");
        }
        Transform.deleteFile(rootPath + "/src/components/Template/" + TempJsx);

    }

    public static String handleComponent(ClassBuilder classBuilder, List<ClassBuilder> classes, Form form,
            String rootPath)
            throws FileNotFoundException {
        String content = Transform.getContent(rootPath + "/src/components/Template/Temp.templ");
        content = content.replace("(data_view)", getDataView(classBuilder, classes, form));
        content = content.replace("(update_view)", getUpdateView(classBuilder, classes, form));
        content = content.replace("(add_view)", getAddView(classBuilder, classes, form));
        content = content.replace("(delete_view)", getDeleteView(classBuilder, classes, form));

        content = content.replace("(className)", classBuilder.getClassName());
        content = content.replace("(ClassName)", classBuilder.getClassName());
        content = content.replace("(ClassNameMin)", Column.minFirst(classBuilder.getClassName()));
        content = content.replace("(pkField)", classBuilder.getPkName());

        return content;
    }

    public static String getDataView(ClassBuilder classBuilder, List<ClassBuilder> classes, Form form)
            throws FileNotFoundException {
        String content = Transform.getContent(form.getDataTemplate());
        // title_field
        String titles = "";
        for (Column column : classBuilder.getColumnsModels()) {
            titles += "\"" + Column.toCamelCase(column.getName()) + "\",";
        }
        titles += "\"\"";
        content = content.replace("(title_field)", titles);
        // fields_indexation
        String fields_index = "";
        for (Column column : classBuilder.getColumnsModels()) {
            fields_index += (column.getIsFK() ? column.getFkLabel(classes) : "\"" + column.getName() + "\"") + ",";
        }
        fields_index += "\"buttons\"";
        content = content.replace("(fields_indexation)", fields_index);

        content = content.replace("(ClassName)", classBuilder.getClassName());

        return content;
    }

    public static String getUpdateView(ClassBuilder classBuilder, List<ClassBuilder> classes, Form form)
            throws FileNotFoundException {
        String content = Transform.getContent(form.getUpdateTemplate());
        content = getUpdateAddContent(classBuilder, classes, form, content);
        return content;
    }

    public static String getUpdateAddContent(ClassBuilder classBuilder, List<ClassBuilder> classes, Form form,
            String content)
            throws FileNotFoundException {
        // state foreign key
        String fkState = "";
        for (Column column : classBuilder.getColumnsModels()) {
            if (column.getIsFK())
                fkState += "const [" + column.getName() + ", set" + Column.CamelCase(column.getName())
                        + "] = useState([]);\n";
        }

        String fkStateFetch = "";
        for (Column column : classBuilder.getColumnsModels()) {
            if (column.getIsFK())
                fkStateFetch += " const get" + Column.CamelCase(column.getName()) + " = async () => {\n" + //
                        "    let res = (await alaivoGet(\"" + column.getName()
                        + "\")).data.map((row) => ({ value: row, label: row."
                        + column.getFkLabelName(classes) + " }));\n"
                        + //
                        "    set" + Column.CamelCase(column.getName()) + "(res);\n" + //
                        "  };";
        }
        // state foreign key
        String fkCallBackFetch = "";
        for (Column column : classBuilder.getColumnsModels()) {
            if (column.getIsFK())
                fkCallBackFetch += "get" + Column.CamelCase(column.getName()) + "();";
        }
        //
        String inputs = "";
        for (Column column : classBuilder.getColumnsModels()) {
            inputs += getColumnInput(column, form);
        }

        content = content.replace("(FkFetch)", fkStateFetch);
        content = content.replace("(stateFk)", fkState);
        content = content.replace("(callbackFkFetch)", fkCallBackFetch);
        content = content.replace("(inputs)", inputs);
        content = content.replace("(pkField)", classBuilder.getPkName());

        return content;
    }

    public static String getColumnInput(Column column, Form form) {
        String content = "";
        if (column.getIsFK()) {
            content = "<Select\n" + //
                    "  onChange={handleInput}\n" + //
                    "  name={\"" + column.getName() + "\"}\n" + //
                    "  fullWidth\n" + //
                    "  title={\"" + Column.CamelCase(column.getName()) + "\"}\n" + //
                    "  optionsType={" + column.getName() + "}\n" + //
                    " />\n";
        } else if (column.getIsPK()) {
        } else {
            content = "<Input onChange={handleInput} type={\"" + form.getTypes().get(column.getType())
                    + "\"} defaultValue={props." + column.getName()
                    + "} name=\"" + column.getName() + "\" fullWidth title={\"" + Column.CamelCase(column.getName())
                    + "\"} />\n";
        }
        return content;
    }

    public static String getAddView(ClassBuilder classBuilder, List<ClassBuilder> classes, Form form)
            throws FileNotFoundException {
        String content = Transform.getContent(form.getAddTemplate());
        content = getUpdateAddContent(classBuilder, classes, form, content);
        return content;
    }

    public static String getDeleteView(ClassBuilder classBuilder, List<ClassBuilder> classes, Form form)
            throws FileNotFoundException {
        String content = Transform.getContent(form.getDeleteTemplate());

        return content;
    }

    public static String handleLinks(List<ClassBuilder> classes, String content) {
        String links = "";
        for (ClassBuilder classBuilder : classes) {
            links += getLink(classBuilder);
        }
        content = content.replace("(navLink_view)", links);
        return content;
    }

    public static String getLink(ClassBuilder classBuilder) {
        String gap = "\t\t\t";
        return gap + "{\n" + //
                gap + "  type: \"link\",\r\n" + //
                gap + "  linkTo: \"/" + Column.minFirst(classBuilder.getClassName()) + "\",\r\n" + //
                gap + "  label: \"" + classBuilder.getClassName() + "\",\r\n" + //
                gap + "  icon: <OverviewIcon />,\r\n" + //
                gap + "},\n";
    }

    public static String handleRouter(List<ClassBuilder> classes, String content) {
        String imports = "";
        String routers = "";
        for (ClassBuilder classBuilder : classes) {
            imports += getRouterImportComponent(classBuilder);
            routers += getRoute(classBuilder);
        }
        content = content.replace("(imports_view)", imports);
        content = content.replace("(routers_view)", routers);
        return content;
    }

    public static String getRouterImportComponent(ClassBuilder classBuilder) {
        return "import " + classBuilder.getClassName() + " from \"./components/Template/"
                + classBuilder.getClassName() + "\";\n";
    }

    public static String getRoute(ClassBuilder classBuilder) {
        String gap = "\t\t\t\t";
        return gap + "<Route\n" + //
                gap + "   path=\"/" + Column.minFirst(classBuilder.getClassName()) + "\"\n" + //
                gap + "   element={\n" + //
                gap + "     <ContentContainer>\n" + //
                gap + "        <" + classBuilder.getClassName() + " />\n" + //
                gap + "     </ContentContainer>\n" + //
                gap + "    }\n" + gap + "  />\n";
    }

    public String getAddTemplate() {
        return addTemplate;
    }

    public HashMap<String, String> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }

    public void setAddTemplate(String addTemplate) {
        this.addTemplate = addTemplate;
    }

    public String getDeleteTemplate() {
        return deleteTemplate;
    }

    public void setDeleteTemplate(String deleteTemplate) {
        this.deleteTemplate = deleteTemplate;
    }

    public String getUpdateTemplate() {
        return updateTemplate;
    }

    public void setUpdateTemplate(String updateTemplate) {
        this.updateTemplate = updateTemplate;
    }

    public String getDataTemplate() {
        return dataTemplate;
    }

    public void setDataTemplate(String dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

}
