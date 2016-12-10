package org.saas.common.mybatis;

import com.google.common.collect.Sets;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

public class LogicGeneratorPlugin extends PluginAdapter {

    private String defaultPrimaryKey = "java.lang.Integer";
    private FullyQualifiedJavaType defaultPrimaryType = null;

    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType("org.saas.common.mybatis.Mapper");
        type.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        if(defaultPrimaryType == null){
            buildPrimaryKeyType(introspectedTable);
        }
        type.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getExampleType()));
        type.addTypeArgument(defaultPrimaryType);
        interfaze.addSuperInterface(type);
        interfaze.getMethods().removeIf(m -> true);

        /**
         * 因为interfaze.getImportedTypes()返回的是一个unmodifiedSet，所以用反射把里面的值清空
         * */
        try {
            java.lang.reflect.Field field = interfaze.getClass().getDeclaredField("importedTypes");
            field.setAccessible(true);
            field.set(interfaze, Sets.newHashSet());
        } catch (Exception e) {
            e.printStackTrace();
        }

        interfaze.addImportedType(type);
        return false;
    }



    private void buildPrimaryKeyType(IntrospectedTable introspectedTable){
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        IntrospectedColumn column = null;
        if(columns != null && columns.size() > 0){
            column = columns.get(0);
        }
        if(column == null) {
            defaultPrimaryType = new FullyQualifiedJavaType(defaultPrimaryKey);
        }else{
            String javaType = "";
            String s = column.getJdbcTypeName().toLowerCase();
            if (s.equals("bigint")) {
                javaType = "java.lang.Long";

            } else if (s.equals("int")) {
                javaType = defaultPrimaryKey;

            } else if (s.equals("nvarchar")) {
                javaType = "java.lang.String";

            } else if (s.equals("varchar")) {
                javaType = "java.lang.String";

            } else {
                javaType = defaultPrimaryKey;

            }
            defaultPrimaryType = new FullyQualifiedJavaType(javaType);
        }
    }
}
