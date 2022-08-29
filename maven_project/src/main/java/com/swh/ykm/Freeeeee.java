package com.swh.ykm;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Freeeeee {
	public static void main(String[] args){
		final String inflearnUrl = String.format("https://movie.naver.com/movie/bi/mi/basic.naver?code=187347");
		Connection conn = Jsoup.connect(inflearnUrl);
		try {
			Document document = conn.get();
			Elements Elements = document.select("div.mv_info_area > div.poster > img"); // div[class=tit3] a
			for (int j = 0; j < Elements.size(); j++) {
				System.out.println( Elements.get(j).attr("src"));
			}
		}
		catch(Exception e) {
			
		}
	}
}