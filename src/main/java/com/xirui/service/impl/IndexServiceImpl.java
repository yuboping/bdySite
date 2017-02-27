package com.xirui.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xirui.entity.Mail;
import com.xirui.mapper.MailMapper;
import com.xirui.service.IndexService;

@Service
public class IndexServiceImpl implements IndexService {

	@Autowired
	MailMapper mailMapper;

	@Override
	public int insert(Map<String, Object> params) {
		Mail mail = new Mail();
		mail.setSmtp("smtp");
		mailMapper.insertSelective(mail);
		return 0;
	}

}
