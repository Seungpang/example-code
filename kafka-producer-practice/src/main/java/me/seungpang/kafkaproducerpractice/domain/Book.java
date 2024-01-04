package me.seungpang.kafkaproducerpractice.domain;

public record Book(
        Integer bookId,
        String bookName,
        String bookAuthor
) {
}
