package com.example.papperdv;

public class Book {
    private String name_book;
    private String Book_author;


    public Book(String name_book, String Book_author) {
        this.Book_author = Book_author;
        this.name_book = name_book;
    }

    public String getBook_author() {
        return Book_author;
    }

    public void setBook_author(String book_author) {
        this.Book_author = book_author;
    }

    public String getName_book() {
        return name_book;
    }

    public void setName_book(String name_book) {
        this.name_book = name_book;
    }
}