package com.betrybe.alexandria.services;

import com.betrybe.alexandria.models.entities.Author;
import com.betrybe.alexandria.models.entities.Book;
import com.betrybe.alexandria.models.entities.BookDetail;
import com.betrybe.alexandria.models.entities.Publisher;
import com.betrybe.alexandria.models.repositories.AuthorRepository;
import com.betrybe.alexandria.models.repositories.BookDetailRepository;
import com.betrybe.alexandria.models.repositories.BookRepository;
import com.betrybe.alexandria.models.repositories.PublisherRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private BookRepository bookRepository;

  private BookDetailRepository bookDetailRepository;

  private final PublisherRepository publisherRepository;

  private final AuthorRepository authorRepository;

  @Autowired
  public BookService(BookRepository bookRepository, BookDetailRepository bookDetailRepository, PublisherRepository publisherRepository,
      AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.bookDetailRepository = bookDetailRepository;
    this.publisherRepository = publisherRepository;
    this.authorRepository = authorRepository;
  }

  public List<Book> findAll(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Book> page = bookRepository.findAll(pageable);

    return page.toList();
  }

  public Book insertBook(Book book) {
    return bookRepository.save(book);
  }

  public Optional<Book> updateBook(Long id, Book book) {
    Optional<Book> optionalBook = bookRepository.findById(id);

    if(optionalBook.isPresent()) {
      Book bookFromDB = optionalBook.get();
      bookFromDB.setTitle(book.getTitle());
      bookFromDB.setGenre(book.getGenre());

      Book updatedBook = bookRepository.save(bookFromDB);
      return Optional.of(updatedBook);

    }

    return optionalBook;
  }

  public Optional<Book> removeBookById(Long id) {
    Optional<Book> bookOptional = bookRepository.findById(id);

    if(bookOptional.isPresent()) {
      bookRepository.deleteById(id);
    }

    return bookOptional;
  }

  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<BookDetail> insertBookDetail(Long bookId, BookDetail bookDetail) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);

    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      bookDetail.setBook(book);
      BookDetail newBookDetail = bookDetailRepository.save(bookDetail);
      return Optional.of(newBookDetail);
    }

    return Optional.empty();
  }


  public Optional<BookDetail> updateBookDetail(Long bookId, BookDetail bookDetail) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);

    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      BookDetail bookDetailFromDB = book.getDetails();
      bookDetailFromDB.setSummary(bookDetail.getSummary());
      bookDetailFromDB.setPageCount(bookDetail.getPageCount());
      bookDetailFromDB.setYear(bookDetail.getYear());
      bookDetailFromDB.setIsbn(bookDetail.getIsbn());

      BookDetail updatedBookDetail = bookDetailRepository.save(bookDetailFromDB);
      return Optional.of(updatedBookDetail);

    }

    return Optional.empty();
  }

  public Optional<BookDetail> removeBookDetailById(Long bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);

    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      Optional<BookDetail> optionalBookDetail = bookDetailRepository.findById(
          book.getDetails().getId());

      if (optionalBookDetail.isPresent()) {
        book.setDetails(null);
        BookDetail bookDetail = optionalBookDetail.get();
        bookDetailRepository.deleteById(bookDetail.getId());

        return Optional.of(bookDetail);
      }
    }

    return Optional.empty();
  }

  public Optional<BookDetail> getBookDetailById(Long id) {
    return bookDetailRepository.findById(id);
  }

  public List<BookDetail> getAllBookDetails() {
    return bookDetailRepository.findAll();
  }

  public Optional<Book> setPublisher(Long bookId, Long publisherId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    if(optionalBook.isEmpty()){
      return Optional.empty();
    }

    Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);
    if(optionalPublisher.isEmpty()){
      return Optional.empty();
    }

    Book book = optionalBook.get();
    Publisher publisher = optionalPublisher.get();

    book.setPublisher(publisher);
    Book updatedBook = bookRepository.save(book);

    return Optional.of(updatedBook);

  }

  public Optional<Book> removePublisher(Long bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    if(optionalBook.isEmpty()){
      return Optional.empty();
    }

    Book book = optionalBook.get();
    book.setPublisher(null);

    Book newBook = bookRepository.save(book);
    return Optional.of(newBook);
  }

  public Optional<Book> setAuthor(Long bookId, Long authorId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    if(optionalBook.isEmpty()){
      return Optional.empty();
    }
    Optional<Author> optionalAuthor = authorRepository.findById(authorId);
    if(optionalAuthor.isEmpty()){
      return Optional.empty();
    }
    Book book = optionalBook.get();
    Author author = optionalAuthor.get();
    book.getAuthors().add(author);
    Book newBook = bookRepository.save(book);
    return Optional.of(newBook);
  }

  public Optional<Book> removeAuthor(Long bookId, long authorId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    if(optionalBook.isEmpty()){
      return Optional.empty();
    }
    Optional<Author> optionalAuthor = authorRepository.findById(authorId);
    if(optionalAuthor.isEmpty()){
      return Optional.empty();
    }
    Book book = optionalBook.get();
    Author author = optionalAuthor.get();
    book.getAuthors().remove(author);
    Book newBook = bookRepository.save(book);
    return Optional.of(newBook);
  }
}