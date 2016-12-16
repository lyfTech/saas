package org.saas.common.enums;

import org.saas.common.utils.StringUtils;

/**
 * 常量枚举
 */
public enum ConsantEnums {
    CURRENT_USERINFO("CURRENT_USERINFO","当前登录用户信息")
    ;


    private String key;
    private String value;

    ConsantEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key){
        if (StringUtils.isNoneBlank(key)){
            for (ConsantEnums enums: values()){
                if (enums.getKey().equals(key)){
                    return enums.getValue();
                }
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
