package com.zh.dao;

import com.zh.po.Myfile;
import com.zh.po.MyfileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyfileMapper {
    int countByExample(MyfileExample example);

    int deleteByExample(MyfileExample example);

    int deleteByPrimaryKey(Long fId);

    int insert(Myfile record);

    int insertSelective(Myfile record);

    List<Myfile> selectByExample(MyfileExample example);

    Myfile selectByPrimaryKey(Long fId);

    int updateByExampleSelective(@Param("record") Myfile record, @Param("example") MyfileExample example);

    int updateByExample(@Param("record") Myfile record, @Param("example") MyfileExample example);

    int updateByPrimaryKeySelective(Myfile record);

    int updateByPrimaryKey(Myfile record);
}