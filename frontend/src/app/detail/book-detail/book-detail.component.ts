import {Component, OnInit} from '@angular/core';
import {Book} from "../../services/book-service/book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../services/book-service/book.service";
import {EditBookComponent} from "../../edit/edit-book/edit-book.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrl: './book-detail.component.css'
})
export class BookDetailComponent implements OnInit {
  public book: Book;

  constructor(private bookService: BookService,
              private dialog: MatDialog,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        console.log('Поиск книги по id :: ' + id);
        this.getBook(+id);
      } else {
        console.error('Книга не найдена по id :: ' + id);
      }
    });
  }

  private getBook(id: number): void {
    this.bookService.getBookById(id).subscribe(
      (response: Book) => {
        this.book = response;
        console.log('Найдена книга для просмотра деталей', response);
      },
      (error) => {
        console.error('Ошибка при поиске книги', error);
      }
    );
  }

  editBook(book: Book): void {
    console.log('Редактирование книги', book);
    const dialogRef = this.dialog.open(EditBookComponent, {
      data: {book: book}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог редактирования книги закрыт');
      this.getBook(book.id);
    });
  }

  deleteBook(bookId: number): void {
    console.log('Удаление книги по id :: ' + bookId);
    this.bookService.deleteBook(bookId).subscribe({
      next: () => {
        console.log('Удалена книга по id ::' + bookId);
        this.router.navigate(['/books']);
      },
      error: err => console.error('Ошибка при удалении книги по id :: ' + bookId, err)
    });
  }

  viewAuthorDetails(authorId: number): void {
    console.log('Просмотр автора по id :: ' + authorId);
    this.router.navigate(['/authors', authorId]);
  }

  viewGenreDetails(genreId: number): void {
    console.log('Просмотр жанра по id :: ' + genreId);
    this.router.navigate(['/genres', genreId]);
  }

}
