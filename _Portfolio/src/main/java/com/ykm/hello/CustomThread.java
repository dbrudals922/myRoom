package com.ykm.hello;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lombok.Data;

@Data
public class CustomThread implements Runnable {
	private String link;
	private Map<String, String> movie;

	public CustomThread(String link, Map<String, String> movie) {
		// TODO Auto-generated constructor stub
		this.link=link;
		this.movie=movie;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Connection conn = Jsoup.connect(this.link);
			Document document = conn.get();
			Element moviePoster = document.select("#content > div.article > div.mv_info_area > div.poster > a > img").first();
//			this.movie.put("poster", moviePoster.attr("src").toString());
			
			if(moviePoster!=null) {
				this.movie.put("poster", moviePoster.attr("src"));
			}
			else {
				this.movie.put("poster", "https://ssl.pstatic.net/static/movie/2012/06/dft_img203x290.png");
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
