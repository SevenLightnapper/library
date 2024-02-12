import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../services/search-service/search.service";
import {BookService} from "../../services/book-service/book.service";
import {Router} from "@angular/router";
import {EditBookComponent} from "../../edit/edit-book/edit-book.component";
import {MatDialog} from "@angular/material/dialog";
import {Book} from "../../services/book-service/book";

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrl: './search-results.component.css'
})
export class SearchResultsComponent implements OnInit {
  searchResults: Book[] = [];

  constructor(private searchService: SearchService,
              private bookService: BookService,
              private dialog: MatDialog,
              private router: Router) {
  }

  ngOnInit() {
    console.log('SearchResultsComponent')
    this.searchService.currentSearchResults.subscribe(results => {
      console.log('Результаты поиска', results);
      this.searchResults = results;
    });
  }

  editBook(book: Book): void {
    console.log('Редактирование книги', book);
    const dialogRef = this.dialog.open(EditBookComponent, {
      data: {book: book}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог обновления книги закрыт');
      console.log('Редирект на страницу отредактированной книги')
      this.router.navigate(['/books', book.id]);
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

  viewBookDetails(bookId: number): void {
    console.log('Просмотр деталей книги по id :: ' + bookId);
    this.router.navigate(['/books', bookId]);
  }

}
