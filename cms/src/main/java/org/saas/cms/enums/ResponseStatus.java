package org.saas.cms.enums;

/**
 * ResponseStatus
 *
 */
public enum ResponseStatus {
    SUCCESS("成功"),
    FAIL("失败");

    private String cnName;

    public String getCnName() {
        return cnName;
    }

    private ResponseStatus(String cnName) {
        this.cnName = cnName;
    }

}
