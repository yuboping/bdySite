package com.xirui.mapper;

import com.xirui.entity.Opinion;

public interface OpinionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Opinion record);

    int insertSelective(Opinion record);

    Opinion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Opinion record);

    int updateByPrimaryKey(Opinion record);
}