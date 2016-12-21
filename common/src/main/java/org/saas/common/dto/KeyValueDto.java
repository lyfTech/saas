package org.saas.common.dto;

/**
 * Created by gls on 2016/12/13.
 */
public class KeyValueDto {

    private Object key;
    private Object value;

    public KeyValueDto(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
