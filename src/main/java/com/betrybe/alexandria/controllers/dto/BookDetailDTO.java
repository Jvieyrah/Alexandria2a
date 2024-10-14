package com.betrybe.alexandria.controllers.dto;

import com.betrybe.alexandria.models.entities.BookDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BookDetailDTO (
    long id,
    String sumary,
    @JsonProperty("page_count")
    Integer pageCount,
    String year,
    String isbn){
  public BookDetail toBookDetail() {
    return new BookDetail(id, sumary, pageCount, year, isbn, null);
  }

}
