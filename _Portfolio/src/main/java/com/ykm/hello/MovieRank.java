package com.ykm.hello;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MovieRank {
	public static List<Map<String, String>> getMovieList(String date) {
		List<Map<String, String>> movies = new ArrayList<Map<String, String>>();
		final String inflearnUrl = String.format("https://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=cnt&tg=0&date=%s", date);
		Connection conn = Jsoup.connect(inflearnUrl);
		List<Thread> threadList = new ArrayList<Thread>();
		try {
			Document document = conn.get();
			List<Integer> ranking = MovieRank.arrangeRank(document);
			int c = 0;
			Elements Elements = document.select("div.tit3 > a"); // div[class=tit3] a
			for (int j = 0; j < Elements.size(); j++) {
				Map<String, String> movie = new HashMap<String, String>();
				final String title = Elements.get(j).text();
				String href = Elements.get(j).attr("href");

				movie.put("name", title);
				movie.put("rank", Integer.toString(ranking.get(c++)));
				movie.put("link", "https://movie.naver.com"+href);

				//Element moviePoster = Jsoup.connect("https://movie.naver.com"+href).get().select("div.article > div.mv_info_area > div.poster > a > img").first();
				//movie.put("poster", moviePoster.attr("src"));

//				Connection conn2 = Jsoup.connect("https://movie.naver.com"+href);
//				Document document2 = conn2.get();
//				Element moviePoster = document.select("div.article > div.mv_info_area > div.poster > a > img").first();
				
				CustomThread subThread = new CustomThread("https://movie.naver.com"+href, movie);
				Thread mainThread = new Thread(subThread);
				mainThread.start();
				threadList.add(mainThread);
				
				movies.add(subThread.getMovie());
			}
			
			for (Thread t : threadList) {
				try {
					t.join();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return movies;
	}

	public static List<Integer> arrangeRank(Document document){
		List<Integer> ranking = new ArrayList<Integer>();
		Elements Elements = document.select("tr > td.ac > img");
		for (int q = 0; q<Elements.size(); q++) {
			boolean isNumber = false;
			String rankI=Elements.get(q).attr("alt");
			try {
				Integer.parseInt(rankI);
				isNumber=true;
			}catch (NumberFormatException e) {
				//do nothing
			}
			if(isNumber) {
				ranking.add(Integer.parseInt(rankI));
			}
		}
		for (int i=1; i<51; i++) {
			if (!ranking.contains(i)) {
				ranking.add(i-1);
			}
		}
		Collections.sort(ranking);
		return ranking;

	}
}
