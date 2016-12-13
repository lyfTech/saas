package org.saas.cms.handle;

import java.util.ArrayList;
import java.util.List;

/**
 * ResponseHandleT
 */
public class ResponseHandleT<T> extends BaseResponseHandle {

    List<T> result = new ArrayList<T>();

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
