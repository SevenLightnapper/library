import {Component, OnInit} from '@angular/core';
import {BookService} from "../../services/book-service/book.service";
import {AddBookComponent} from "../../add/add-book/add-book.component";
import {MatDialog} from "@angular/material/dialog";
import {EditBookComponent} from "../../edit/edit-book/edit-book.component";
import {Book} from "../../services/book-service/book";
import {Router} from "@angular/router";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrl: './books.component.css'
})
export class BooksComponent implements OnInit {
  books: Book[] = [];

  constructor(private bookService: BookService,
              private router: Router,
              private dialog: MatDialog) {  }

  ngOnInit(): void {
    this.getAllBooks();
  }

  getAllBooks(): void {
    this.bookService.getAllBooks().subscribe({
      next: data => {
        console.log('Все книги', data);
        this.books = data;
      },
      error: err => console.error('Ошибка при загрузке списка книг', err)
    });
  }

  addBook(): void {
    console.log('Добавление книги');
    const dialogRef = this.dialog.open(AddBookComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог добавления книги закрыт');
      this.getAllBooks();
    });

  }

  editBook(book: Book): void {
    console.log('Редактирование книги', book);
    const dialogRef = this.dialog.open(EditBookComponent, {
      data: {book: book}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог редактирования книги закрыт');
      this.getAllBooks();
    });
  }

  deleteBook(bookId: number): void {
    console.log('Удаление книги по id :: ' + bookId);
    this.bookService.deleteBook(bookId).subscribe({
      next: () => {
        console.log('Удалена книга по id ::' + bookId);
        this.getAllBooks();
      },
      error: err => console.error('Ошибка при удалении книги по id :: ' + bookId, err)
    });
  }

  viewBookDetails(bookId: number): void {
    console.log('Просмотр деталей книги по id :: ' + bookId);
    this.router.navigate(['/books', bookId]);
  }

}
