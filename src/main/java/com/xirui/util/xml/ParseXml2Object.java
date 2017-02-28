package com.xirui.util.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
 * <p>Title:ParseXml2Object</p>
 * <p>Description:xml转对象</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年5月25日上午11:08:14
 */
public class ParseXml2Object {

	private Document doc = null;


	public Document createDocument(String xmlStr) throws DocumentException {
		doc = DocumentHelper.parseText(xmlStr);
		return doc;
	}

	
	public Map<String, Object> getMsgDataToDoc(String xmlStr) throws DocumentException {
		Map<String, Object> result = new HashMap<String, Object>();
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		List<?> elements = root.elements();

		if (0 < elements.size()) {
			result.put(root.getName(), getData(root));
		} else {
			result.put(root.getName(), root.getTextTrim());
		}

		return result;
	}

	
	public Map<String, Object> getMsgData(String xmlStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		Element root = doc.getRootElement();
		List<?> elements = root.elements();

		if (0 < elements.size()) {
			result.put(root.getName(), getData(root));
		} else {
			result.put(root.getName(), root.getTextTrim());
		}

		return result;
	}

	
	public Map<String, Object> getMapsData(String xmlStr) throws DocumentException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		return getMapData(root, resultMap);
	}


	@SuppressWarnings("unchecked")
	private Map<String, Object> getData(Element element) {
		List<?> elements = element.elements();
		Map<String, Object> retMap = new HashMap<String, Object>();

		for (int i = 0; i < elements.size(); i++) {
			Element nestElement = (Element) elements.get(i);

			String key = nestElement.getName();

			if (0 < nestElement.elements().size()) {
				if (null == retMap.get(key)) {
					retMap.put(key, getData(nestElement));
				} else {
					List<Object> nestList = null;
					if (retMap.get(key) instanceof List) {
						nestList = (List<Object>) retMap.get(key);
					} else {
						nestList = new ArrayList<Object>();
						nestList.add(retMap.get(key));
					}
					nestList.add(getData(nestElement));
					retMap.put(nestElement.getName(), nestList);
				}

			} else {
				if (null == retMap.get(key)) {
					retMap.put(key, "".equals(nestElement.getTextTrim()) ? null : nestElement.getTextTrim());
				} else {
					List<Object> nestList = null;
					if (retMap.get(key) instanceof List) {
						nestList = (List<Object>) retMap.get(key);
					} else {
						nestList = new ArrayList<Object>();
						nestList.add(retMap.get(key));
					}
					nestList.add(nestElement.getTextTrim());
					retMap.put(nestElement.getName(), nestList);
				}
			}
		}
		return retMap;
	}

	
	private Map<String, Object> getMapData(Element element, Map<String, Object> resultMap) {
		List<?> elements = element.elements();
		for (int i = 0; i < elements.size(); i++) {
			Element nestElement = (Element) elements.get(i);
			String key = nestElement.getName();
			if (0 < nestElement.elements().size()) {
				getMapData(nestElement, resultMap);
			} else {
				if (null == resultMap.get(key)) {
					resultMap.put(key, "".equals(nestElement.getTextTrim()) ? null : nestElement.getTextTrim());
				}
			}
		}
		return resultMap;
	}

}
