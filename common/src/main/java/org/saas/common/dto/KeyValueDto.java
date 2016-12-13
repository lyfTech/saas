package org.saas.common.dto;

/**
 * Created by gls on 2016/12/13.
 */
public class KeyValueDto {

    private String key;
    private String value;

    public KeyValueDto(String key, String value) {
        this.key = key;
        this.value = value;
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
