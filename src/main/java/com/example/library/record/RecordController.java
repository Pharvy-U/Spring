package com.example.library.record;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path="record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public List<Record> showBooks(){
        return recordService.getBooks();
    }

    @GetMapping(path = "getbyid/{bookId}")
    public Record showBook(@PathVariable("bookId") Long bookId){
        return recordService.getBook(bookId);
    }

    @GetMapping(path = "getbyname/{title}")
    public Record showBook(@PathVariable("title") String title){
        return recordService.getBook(title);
    }

    @PostMapping
    public void newStock(@RequestBody Record record){
        recordService.stack(record);
    }

    //    interact by id

    @DeleteMapping(path="removebyid/{bookId}")
    public void  removeBooks(@PathVariable("bookId") Long bookId){
        recordService.removeBook(bookId);
    }

    @PutMapping(path="updatebyid/{bookId}")
    public void refactorRecord(@PathVariable("bookId") Long bookId,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String author,
                               @RequestParam(required = false) Long quantity){
        recordService.editBookInfo(bookId, title, author, quantity);
    }

    @PutMapping(path="borrowbyid/{bookId}")
    public void borrowBook(@PathVariable("bookId") Long bookId){
        recordService.borrowedBook(bookId);
    }

    @PutMapping(path="returnbyid/{bookId}")
    public void returnBook(@PathVariable("bookId") Long bookId){
        recordService.returnedBook(bookId);
    }

    //    interact by book name

    @DeleteMapping(path="removebyname/{bookName}")
    public void  removeBooks(@PathVariable("bookName") String bookName){
        recordService.removeBook(bookName);
    }

    @PutMapping(path="updatebyname/{bookName}")
    public void refactorRecord(@PathVariable("bookName") String bookName,
                               @RequestParam(required = false) String newTitle,
                               @RequestParam(required = false) String author,
                               @RequestParam(required = false) Long quantity){
        recordService.editBookInfo(bookName, newTitle, author, quantity);
    }

    @PutMapping(path="borrowbyname/{bookName}")
    public void borrowBook(@PathVariable("bookName") String bookName){
        recordService.borrowedBook(bookName);
    }

    @PutMapping(path="returnbyname/{bookName}")
    public void returnBook(@PathVariable("bookName") String bookName){
        recordService.returnedBook(bookName);
    }

}
