package org.saas.common.handle;

import org.saas.common.enums.ResponseStatus;

import java.io.Serializable;

/**
 * BaseResponseHandle
 */
public class BaseResponseHandle implements Serializable {


    private static final long serialVersionUID = 8813399895014678175L;
    /**
     * 默认返回
     */
    private ResponseStatus status = ResponseStatus.SUCCESS;


    /**
     * 返回信息
     */
    private String message;


    /**
     * 判断是否成功
     *
     * @return
     */
    public boolean getIsSuccess() {
        if (status.name().equals(ResponseStatus.SUCCESS.name())) {
            return true;
        }
        return false;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 如果service层报错，则设置这个信息
     *
     * @param message
     */
    public void setErrorMessage(String message) {
        status = ResponseStatus.FAIL;
        this.message = message;
    }
}
