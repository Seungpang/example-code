package com.group.libraryapp.dto.book.request;

public class BookReturnRequest {

  private String userName;
  private String bookName;

  public BookReturnRequest(final String userName, final String bookName) {
    this.userName = userName;
    this.bookName = bookName;
  }

  public String getUserName() {
    return userName;
  }

  public String getBookName() {
    return bookName;
  }

}
