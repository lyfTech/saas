package org.saas.common.enums;

import org.saas.common.utils.StringUtils;

/**
 * 权限类型
 * 常量枚举
 */
public enum PermTypeEnums {
    MENU(1,"菜单"),
    BUTTON(2,"按钮")
    ;


    private Integer key;
    private String value;

    PermTypeEnums(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key){
        if (StringUtils.isNoneBlank(key)){
            for (PermTypeEnums enums: values()){
                if (enums.getKey().equals(key)){
                    return enums.getValue();
                }
            }
        }
        return null;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
