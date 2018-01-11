package co.nextwireless.swagger;

import io.swagger.codegen.*;
import io.swagger.codegen.languages.SpringCodegen;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.properties.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 4/20/2017
 * Time: 5:19 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
public class NWScalaCodegen extends SpringCodegen {
    public NWScalaCodegen() {
        super();
        interfaceOnly = true;

        setSourceFolder(projectFolder + File.separator + "scala");
        setTestFolder(projectTestFolder + File.separator + "scala");
        modelTemplateFiles.put("model.mustache", ".scala");
        apiTemplateFiles.put("api.mustache", ".scala");
//        apiTestTemplateFiles.put("api_test.mustache", ".scala");

        additionalProperties.put("java8", "true");

        languageSpecificPrimitives = new HashSet<String>(
                Arrays.asList(
                        "String",
                        "List",
                        "boolean",
                        "Boolean",
                        "Double",
                        "Integer",
                        "Long",
                        "Float",
                        "Object",
                        "byte[]")
        );


        setReservedWordsLowerCase(Arrays.asList("abstract", "continue", "for", "new", "switch", "assert", "default", "if", "package", "synchronized", "boolean", "do", "goto", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while", "type"));
    }

    @Override
    public void processOpts() {
        super.processOpts();

        importMapping.clear();

//        instantiationTypes.put("array", "List");
        instantiationTypes.put("map", "Map");

//        typeMapping.put("FeatureTestResult", "FeatureTestResult.FeatureTestResult");
        importMapping.remove("ApiModelProperty");
        importMapping.remove("ApiModel");

        importMapping.remove("List");
        typeMapping.put("array", "List");
        typeMapping.put("map", "scala.Map");

        typeMapping.put("integer", "Int");
        importMapping.put("Int", "scala.Int");

        typeMapping.put("date", "LocalDate");
        importMapping.put("LocalDate", "java.time.LocalDate");

        typeMapping.put("DateTime", "ZonedDateTime");
        importMapping.put("ZonedDateTime", "java.time.ZonedDateTime");

        importMapping.put("OffsetDateTime", "java.time.OffsetDateTime");

        importMapping.put("BigDecimal", "scala.BigDecimal");

    }

    @Override
    public String getName() {
        return "pap-scala";
    }

    @Override
    public CodegenProperty fromProperty(String name, Property p) {
        CodegenProperty codegenProperty = super.fromProperty(name, p);
        if (codegenProperty.isEnum)
            enumImports.add(codegenProperty.datatypeWithEnum);
        return codegenProperty;
    }

    @Override
    public Map<String, Object> postProcessOperations(Map<String, Object> objs) {
        Map<String, Object> stringObjectMap = super.postProcessOperations(objs);

        Map<String, Object> operations = (Map<String, Object>) stringObjectMap.get("operations");
        if (operations != null) {
            List<CodegenOperation> ops = (List<CodegenOperation>) operations.get("operation");
            for (CodegenOperation operation : ops) {
                if (operation.returnType == null || operation.returnType.equals("Void")) {
                    operation.returnType = "Unit";
                } else if (operation.returnType.startsWith("List")) {
                    String rt = operation.returnType;
                    int end = rt.lastIndexOf("]");
                    if (end > 0) {
                        operation.returnType = rt.substring("List[".length(), end).trim();
                        System.out.println("operation.returnType + rt = " + operation.returnType + " " + rt);
                        operation.returnContainer = "List";
                        String varName = operation.returnType.substring(0, 1).toLowerCase() + operation.returnType.substring(1) + "s";
                        operation.vendorExtensions.put("returnTypeVar", toVarName(varName));
                    }
                }
            }
        }
        return stringObjectMap;
    }

    @Override
    public String toEnumVarName(String value, String datatype) {
        String varName = toEnumVarNameNoCasing(value, datatype);
        if (varName.equals("new")) varName = "`new`";
        return varName;
    }

    private String toEnumVarNameNoCasing(String value, String datatype) {
        if (value.length() == 0) {
            return "EMPTY";
        }

        // for symbol, e.g. $, #
        if (getSymbolName(value) != null) {
            return getSymbolName(value);
        }

        // number
        if ("Integer".equals(datatype) || "Long".equals(datatype) ||
                "Float".equals(datatype) || "Double".equals(datatype)) {
            String varName = "NUMBER_" + value;
            varName = varName.replaceAll("-", "MINUS_");
            varName = varName.replaceAll("\\+", "PLUS_");
            varName = varName.replaceAll("\\.", "_DOT_");
            return varName;
        }

        // string
        String var = value.replaceAll("\\W+", "_");
        if (var.matches("\\d.*")) {
            return "_" + var;
        } else {
            return var;
        }
    }


    @Override
    public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
        super.postProcessModelProperty(model, property);
        model.imports = model.imports.stream()
                .filter(imp-> !imp.contains("Json"))
                .filter(imp-> !imp.contains("Jackson"))
                .filter(imp-> !imp.contains("ArrayList"))
                .filter(imp-> !imp.contains("ApiModelProperty"))
                .filter(imp-> !imp.contains("ApiModel"))
                .collect(Collectors.toSet());
        if (property.baseType.equals("FeatureTestResult")) {
            // we serialize BigDecimal as `string` to avoid precision loss
            property.vendorExtensions.put("isExternalEnum", true);
        }
    }

    private final List<String> enumImports = new ArrayList<>();


    @Override
    public String getTypeDeclaration(Property p) {
        if (p instanceof ArrayProperty) {
            ArrayProperty ap = (ArrayProperty) p;
            Property inner = ap.getItems();
            if (inner == null) {
                LOGGER.warn(ap.getName() + "(array property) does not have a proper inner type defined");
                // TODO maybe better defaulting to StringProperty than returning null
                return null;
            }

            return getSwaggerType(p) + "[" + getTypeDeclaration(inner) + "]";
        } else if (p instanceof MapProperty) {
            MapProperty mp = (MapProperty) p;
            Property inner = mp.getAdditionalProperties();
            if (inner == null) {
                LOGGER.warn(mp.getName() + "(map property) does not have a proper inner type defined");
                // TODO maybe better defaulting to StringProperty than returning null
                return null;
            }

            return getSwaggerType(p) + "[String, " + getTypeDeclaration(inner) + "]";
        }

        return super.getTypeDeclaration(p);
    }

    private List<String> pathToHttp4sRoute(String path) {
        // Map the capture params by their names.

        // Cut off the leading slash, if it is present.
        if (path.startsWith("/")) {
            path = path.substring(1);
        }


        // Convert the path into a list of servant route components.
        List<String> pathComponents = new ArrayList<>();
        for (String piece : path.split("/")) {
            if (piece.startsWith("{") && piece.endsWith("}")) {
                String name = piece.substring(1, piece.length() - 1);
                pathComponents.add(" " + name + " ");
            } else {
                pathComponents.add("\"" + piece + "\"");
            }

        }


        // Intersperse the servant route pieces with :> to construct the final API type
        return pathComponents;
    }

    @Override
    public CodegenOperation fromOperation(String resourcePath, String httpMethod, Operation operation, Map<String, Model> definitions, Swagger swagger) {
        CodegenOperation op = super.fromOperation(resourcePath, httpMethod, operation, definitions, swagger);
        op.vendorExtensions.put("x-enumImports", enumImports);//.stream().collect(Collectors.joining("", "import " + modelPackage + ".", "\\n")));

        List<String> path = pathToHttp4sRoute(op.path);

        List<String> type = pathToClientType(op.path, op.pathParams);
        String pathString = path.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(" / "));

        // Query parameters appended to routes
        if (!op.queryParams.isEmpty()) {
            String queryParams = op.queryParams.stream()
                    .filter(codegenParameter -> !codegenParameter.baseName.isEmpty())
                    .map(codegenParameter -> (codegenParameter.required ? " +?" : " +??") + " (\"" + codegenParameter.baseName + "\", " + codegenParameter.baseName + ")")
                    .collect(Collectors.joining());
            System.out.println("queryParams = " + queryParams);
            pathString = pathString + queryParams;
        }

        // Either body or form data parameters appended to route
        // As far as I know, you cannot have two ReqBody routes.
        // Is it possible to have body params AND have form params?
        String bodyType = null;
        if (op.getHasBodyParam()) {
            for (CodegenParameter param : op.bodyParams) {
                path.add("ReqBody '[JSON] " + param.dataType);
                bodyType = param.dataType;
            }
        } else if (op.getHasFormParams()) {
            // Use the FormX data type, where X is the conglomerate of all things being passed
            String formName = "Form" + camelize(op.operationId);
            bodyType = formName;
            path.add("ReqBody '[FormUrlEncoded] " + formName);
        }
        if (bodyType != null) {
            type.add(bodyType);
        }

        // Special headers appended to route
        for (CodegenParameter param : op.headerParams) {
            path.add("Header \"" + param.baseName + "\" " + param.dataType);

            String paramType = param.dataType;
            if (param.isListContainer) {
                paramType = makeQueryListType(paramType, param.collectionFormat);
            }
            type.add("Maybe " + paramType);
        }
        op.vendorExtensions.put("x-routeType", pathString);

        return op;
    }

    private String makeQueryListType(String type, String collectionFormat) {
        type = type.substring(1, type.length() - 1);
        switch (collectionFormat) {
            case "csv":
                return "(QueryList 'CommaSeparated (" + type + "))";
            case "tsv":
                return "(QueryList 'TabSeparated (" + type + "))";
            case "ssv":
                return "(QueryList 'SpaceSeparated (" + type + "))";
            case "pipes":
                return "(QueryList 'PipeSeparated (" + type + "))";
            case "multi":
                return "(QueryList 'MultiParamArray (" + type + "))";
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toVarName(String name) {
        // sanitize name
        name = sanitizeName(name); // FIXME: a parameter should not be assigned. Also declare the methods parameters as 'final'.

        if (name.toLowerCase().matches("^_*class$")) {
            return "propertyClass";
        }

        if ("_".equals(name)) {
            name = "_u";
        }

        // if it's all uppper case, do nothing
        if (name.matches("^[A-Z_]*$")) {
            return name;
        }

        // for reserved word or word starting with number, append _
        if (isReservedWord(name) || name.matches("^\\d.*")) {
            name = escapeReservedWord(name);
        }

        return name;
    }


    @Override
    public String escapeReservedWord(String name) {
        if (this.reservedWordsMappings().containsKey(name)) {
            return this.reservedWordsMappings().get(name);
        }
        return "`" + name + "`";
    }


    private List<String> pathToClientType(String path, List<CodegenParameter> pathParams) {
        // Map the capture params by their names.
        HashMap<String, String> captureTypes = new HashMap<>();
        for (CodegenParameter param : pathParams) {
            captureTypes.put(param.baseName, param.dataType);
        }

        // Cut off the leading slash, if it is present.
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        // Convert the path into a list of servant route components.
        List<String> type = new ArrayList<>();
        for (String piece : path.split("/")) {
            if (piece.startsWith("{") && piece.endsWith("}")) {
                String name = piece.substring(1, piece.length() - 1);
                type.add(captureTypes.get(name));
            }
        }

        return type;
    }
}
