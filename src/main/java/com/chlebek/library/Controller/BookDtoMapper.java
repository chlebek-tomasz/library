package com.chlebek.library.Controller;

import com.chlebek.library.Controller.Dto.BookDto;
import com.chlebek.library.Model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookDtoMapper {
    private BookDtoMapper(){
    }

    public static List<BookDto> mapToBookDtos(List<Book> books){
        return books.stream()
                .map(book -> mapToBookDto(book))
                .collect(Collectors.toList());
    }

    private static BookDto mapToBookDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setDescription(book.getDescription());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublishingYear(book.getPublishingYear());
        return bookDto;
    }
}
