package org.saas.common.handle;

/**
 * 返回单个DTO
 * SingleResponseHandleT
 */
public class SingleResponseHandleT<T> extends BaseResponseHandle {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
