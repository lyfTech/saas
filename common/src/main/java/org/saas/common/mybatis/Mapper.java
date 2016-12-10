package org.saas.common.mybatis;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;

public interface Mapper<T, C ,PK extends Serializable> {

    int countByExample(C criteria);

    int deleteByExample(C criteria);

    int deleteByPrimaryKey(PK id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExampleWithRowbounds(C criteria, RowBounds rowBounds);

    List<T> selectByExample(C criteria);

    <T> T selectByPrimaryKey(PK id);

    List<T> selectByExampleWithBLOBs(C criteria, RowBounds rowBounds);

    List<T> selectByExampleWithBLOBs(C criteria);
    
    int updateByExampleSelective(@Param("record") T record, @Param("example") C criteria);

    int updateByExample(@Param("record") T record, @Param("example") C criteria);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
