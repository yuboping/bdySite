package com.xirui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@RequestMapping("weixin")
	public void weixin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParams(request);
		logger.info(params.get("signature").toString());
		logger.info(params.get("timestamp").toString());
		logger.info(params.get("nonce").toString());
		logger.info(params.get("echostr").toString());
		writetoclient(params.get("echostr").toString(), response);
	}

	@ResponseBody
	@RequestMapping("mail")
	public String mail(HttpServletRequest request) {
		logger.info("----------点击----------");
		Map<String, Object> params = getParams(request);
		return params.get("smtp").toString();
	}
}
