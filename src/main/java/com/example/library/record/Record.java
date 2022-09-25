package com.example.library.record;

import javax.persistence.*;

@Entity
@Table
public class Record {
    @Id
    @SequenceGenerator(
            name = "record_sequence",
            sequenceName = "record_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "record_sequence"
    )
    private Long id;
    private String title;
    private String author;
    private Long borrowed;
    @Transient
    private Long available;
    private Long quantity;

    public Record(){
    }

    public Record(Long id, String title, String author, Long borrowed, Long quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = borrowed;
//        this.available = quantity - borrowed;
        this.quantity = quantity;
    }

    public Record(String title, String author, Long borrowed, Long quantity) {
        this.title = title;
        this.author = author;
        this.borrowed = borrowed;
//        this.available = quantity - borrowed;
        this.quantity = quantity;
    }

//    public Record(String title, String author, Long quantity) {
//        this.title = title;
//        this.author = author;
//        this.borrowed = 0L;
////        this.available = quantity - borrowed;
//        this.quantity = quantity;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Long borrowed) {
        this.borrowed = borrowed;
    }

    public Long getAvailable() {
        return this.quantity - this.borrowed;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowed=" + borrowed +
                ", available=" + available +
                ", quantity=" + quantity +
                '}';
    }
}