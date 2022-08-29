package com.swh.bootstrapP;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple CRUD example showing how to create, get, update and delete book resources.
 */
public class Books {

	/**
	 * Map holding the books
	 */
	private static Map<String, Book> books = new HashMap<String, Book>();

	public static void main(String[] args) {
		final Random random = new Random();

		// Creates a new book resource, will return the ID to the created resource
		// author and title are sent in the post body as x-www-urlencoded values e.g. author=Foo&title=Bar
		// you get them by using request.queryParams("valuename")
		post("/books", (request, response) -> {
			String author = request.queryParams("author");
			String title = request.queryParams("title");
			Book book = new Book(author, title);

			int id = random.nextInt(Integer.MAX_VALUE);
			books.put(String.valueOf(id), book);

			response.status(201); // 201 Created
			return id;
		});

		// Gets the book resource for the provided id
		get("/books/:id", (request, response) -> {
			Book book = books.get(request.params(":id"));
			if (book != null) {
				return "Title: " + book.getTitle() + ", Author: " + book.getAuthor();
			} else {
				response.status(404); // 404 Not found
				return "Book not found";
			}
		});
	}

	public static class Book {

		public String author, title;

		public Book(String author, String title) {
			this.author = author;
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}