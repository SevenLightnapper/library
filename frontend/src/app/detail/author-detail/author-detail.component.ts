import {Component, OnInit} from '@angular/core';
import {Author} from "../../services/author-service/author";
import {AuthorService} from "../../services/author-service/author.service";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {EditAuthorComponent} from "../../edit/edit-author/edit-author.component";
import {Book} from "../../services/book-service/book";
import {BookService} from "../../services/book-service/book.service";

@Component({
  selector: 'app-author-detail',
  templateUrl: './author-detail.component.html',
  styleUrl: './author-detail.component.css'
})
export class AuthorDetailComponent implements OnInit {
  author: Author;
  books: Book[];

  constructor(private authorService: AuthorService,
              private bookService: BookService,
              private dialog: MatDialog,
              private router: Router,
              private route: ActivatedRoute) {  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        console.log('Поиск автора по id :: ' + id);
        this.getAuthor(+id);
        this.getAuthorBooks(+id);
      } else {
        console.error('Автор не найден по id :: ' + id);
      }
    });
  }

  private getAuthor(id: number): void {
    this.authorService.getAuthorById(id).subscribe(
      (response: Author) => {
        this.author = response;
        console.log('Найден автор для просмотра деталей', response);
      },
      error => {
        console.error('Ошибка при поиске автора по id :: ' + id, error);
      }
    );
  }

  editAuthor(author: Author): void {
    console.log('Редактирование автора', author);
    const dialogRef = this.dialog.open(EditAuthorComponent, {
      data: {author: author}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог редактирования автора закрыт');
      this.getAuthor(author.id);
    });
  }

  deleteAuthor(authorId: number): void {
    console.log('Удаление автора по id :: ' + authorId);
    this.authorService.deleteAuthor(authorId).subscribe({
      next: () => {
        console.log('Удален автор по id :: ' + authorId);
        this.router.navigate(['/authors']);
      },
      error: err => console.error('Ошибка при удалении автора по id :: ' + authorId, err)
    });
  }

  getAuthorBooks(authorId: number): void {
    console.log('Получение списка книг автора :: ' + authorId);
    this.bookService.getBooksByAuthorId(authorId).subscribe(
      books => {
        this.books = books;
        console.log('Получены книги автора :: ' + authorId, this.books);
      },
      error => {
        console.error('Ошибка при поиске книг автора :: ' + authorId, error);
      }
    );
  }

  viewBookDetails(bookId: number): void {
    console.log('Просмотр деталей книги по id :: ' + bookId);
    this.router.navigate(['/books', bookId]);
  }

}
