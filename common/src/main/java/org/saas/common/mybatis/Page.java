package org.saas.common.mybatis;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 分页DTO
 *
 * @param <T>
 */
public class Page<T> implements Serializable {

    private final PageRequest pageRequest;
    private Integer total;
    private final List<T> rows = new ArrayList<T>();


    public Page(List<T> rows, PageRequest pageRequest, Integer total) {
        this.rows.addAll(rows);
        this.total = total;
        this.pageRequest = pageRequest;
    }

    /**
     * 迭代器
     *
     * @return
     */
    public Iterator<T> iterator() {
        return rows.iterator();
    }

    /**
     * 当前页元素列表
     *
     * @return
     */
    public List<T> getList() {
        return Collections.unmodifiableList(rows);
    }

    /**
     * 当前页是否有元素
     *
     * @return
     */
    public boolean hasContent() {
        return !rows.isEmpty();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
