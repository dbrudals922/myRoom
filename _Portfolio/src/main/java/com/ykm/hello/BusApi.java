package com.ykm.hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BusApi {

	// tag값의 정보를 가져오는 메소드
	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	public List<Map<String, String>> getBusStep1(int busNum) {
		List<Map<String, String>> busesList = new ArrayList<Map<String, String>>();
		try {
			// parsing할 url 지정(API 키 포함해서)
			String url = "http://apis.data.go.kr/6410000/busrouteservice/getBusRouteList?serviceKey=0AFbuL2vD3w%2BmPYxv7wngHxV15sYkff8KFECgmkncga%2FlYSFookF8j870zhAqNxb4e7yZ99r0xH%2BgDE7M7wf8A%3D%3D&keyword="
					+ busNum;

			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);

			// root tag
			doc.getDocumentElement().normalize();
//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("busRouteList");
			// System.out.println("파싱할 리스트 수 : "+ nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Map<String, String> bus = new HashMap<String, String>();
					Element eElement = (Element) nNode;
					bus.put("districtCd", getTagValue("districtCd", eElement));
					bus.put("regionName", getTagValue("regionName", eElement));
					bus.put("routeId", getTagValue("routeId", eElement));
					bus.put("routeName", getTagValue("routeName", eElement));
					bus.put("routeTypeCd", getTagValue("routeTypeCd", eElement));
					bus.put("routeTypeName", getTagValue("routeTypeName", eElement));

					busesList.add(bus);
				} // if end
			} // for end

		} catch (

		Exception e) {
			e.printStackTrace();
		} // try~catch end
		return busesList;
	} // main end
} // class end