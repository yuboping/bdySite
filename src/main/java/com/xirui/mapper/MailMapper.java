package com.xirui.mapper;

import com.xirui.entity.Mail;

public interface MailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record);
}