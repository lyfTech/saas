package org.saas.common.mybatis;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;

/**
 * 分页请求对象
 */
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -7767909042764475002L;

    private int offset = 1;
    private int limit = 10;

    public PageRequest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public RowBounds getRowBounds() {
        return new RowBounds(offset, limit);
    }
}