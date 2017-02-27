package com.xirui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xirui.service.IndexService;
import com.xirui.util.config.Global;
import com.xirui.util.controller.BaseController;

@Controller
@RequestMapping(value = Global.FRONT_PATH)
public class IndexController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	@Autowired
	IndexService indexService;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		logger.info("----------进入西瑞公司主页----------");
		Map<String, Object> params = getParams(request);
		indexService.insert(params);
		return "index";
	}

	@ResponseBody
	@RequestMapping("user")
	public String user(HttpServletRequest request) {
		return "user";
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		return "login";
	}
}
