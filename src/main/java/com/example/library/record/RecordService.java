package com.example.library.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class RecordService {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getBooks() {
        return recordRepository.findAll();
    }

    public void stack(Record record) {
        Optional<Record> any = recordRepository.findByTitle(record.getTitle());
        if(any.isPresent()){
            throw new IllegalStateException("Book with title" + record.getTitle() + "is already present");
        }
        recordRepository.save(record);
    }

    public void removeBook(Long bookId) {
        recordRepository.deleteById(bookId);
    }

    @Transactional
    public void editBookInfo(Long bookId, String title, String author, Long quantity) {
        Record record = recordRepository.findById(bookId).orElseThrow(() ->
                        new IllegalStateException("Book with id" + bookId + "does not exist"));

        if(title != null && title.length() > 0 && !Objects.equals(title.toLowerCase(), record.getTitle().toLowerCase())){
            Optional<Record> any = recordRepository.findByTitle(title);
            if(any.isPresent()){
                throw new IllegalStateException("Book with title" + title + "is already present");
            }
            record.setTitle(title);
        }

        if(author != null && author.length() > 0 && !Objects.equals(author.toLowerCase(), record.getAuthor().toLowerCase())){
            record.setAuthor(author);
        }

        if(quantity > 0 && !quantity.equals(record.getQuantity())){
            record.setQuantity(quantity);
        }
    }

    @Transactional
    public void borrowedBook(Long bookId) {
        Record record = recordRepository.findById(bookId).orElseThrow(() ->
                        new IllegalStateException("Book with id" + bookId + "does not exist"));

        if(record.getAvailable() <= 0){
            throw new IllegalStateException("This book is no longer available for borrowing");
        }

        record.setBorrowed(record.getBorrowed() + 1);
    }

    @Transactional
    public void returnedBook(Long bookId) {
        Record record = recordRepository.findById(bookId).orElseThrow(() ->
                new IllegalStateException("Book with id" + bookId + "does not exist"));

        if(record.getBorrowed() <= 0){
            throw new IllegalStateException("Are you sure you got this book from this library?");
        }

        record.setBorrowed(record.getBorrowed() - 1);
    }

    public void removeBook(String bookName) {
        Optional<Record> record = recordRepository.findByTitle(bookName);
        if(record.isEmpty()){
            throw new IllegalStateException("Book with title" + bookName + "not found");
        }
        recordRepository.delete(record.get());
    }

    @Transactional
    public void editBookInfo(String title, String newTitle, String author, Long quantity) {
        Optional<Record> rec = recordRepository.findByTitle(title);

        if(rec.isEmpty()){
            throw new IllegalStateException("Book with title" + title + "not found");
        }
        Record record = rec.get();

        if(newTitle != null && newTitle.length() > 0 && !Objects.equals(newTitle.toLowerCase(), record.getTitle())){
            Optional<Record> any = recordRepository.findByTitle(newTitle);
            if(any.isPresent()){
                throw new IllegalStateException("Book with title" + title + "is already present");
            }
            record.setTitle(newTitle);
        }

        if(author != null && author.length() > 0 && !Objects.equals(author.toLowerCase(), record.getAuthor())){
            record.setAuthor(author.toLowerCase());
        }

        if(quantity > 0 && !quantity.equals(record.getQuantity())){
            record.setQuantity(quantity);
        }
        else if(quantity == 0){
            recordRepository.deleteById(record.getId());
        }
        else{
            throw new IllegalStateException("Quantity cannot be a negative number");
        }

    }

    @Transactional
    public void borrowedBook(String title) {
        Optional<Record> rec = recordRepository.findByTitle(title);

        if(rec.isEmpty()){
            throw new IllegalStateException("Book with title" + title + "not found");
        }
        Record record = rec.get();

        if(record.getAvailable() <= 0){
            throw new IllegalStateException("This book is no longer available for borrowing");
        }
        Long borrow = record.getBorrowed();
        record.setBorrowed(borrow + 1);
    }

    @Transactional
    public void returnedBook(String title) {
        Optional<Record> rec = recordRepository.findByTitle(title);

        if(rec.isEmpty()){
            throw new IllegalStateException("Book with title" + title + "not found");
        }
        Record record = rec.get();

        if(record.getBorrowed() <= 0){
            throw new IllegalStateException("Are you sure you got this book from this library?");
        }

        Long borrow = record.getBorrowed();
        record.setBorrowed(borrow - 1);
    }

    public Record getBook(Long bookId) {
        Record record = recordRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("Book with id" + bookId + "not found"));
        return record;
    }

    public Record getBook(String title) {
        Optional<Record> rec = recordRepository.findByTitle(title);
        if(rec.isEmpty()){
            throw new IllegalStateException("Book with title" + title + "not found");
        }
        return rec.get();
    }
}
