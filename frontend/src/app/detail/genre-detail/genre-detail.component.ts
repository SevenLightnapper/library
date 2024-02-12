import {Component, OnInit} from '@angular/core';
import {Genre} from "../../services/genre-service/genre";
import {GenreService} from "../../services/genre-service/genre.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Book} from "../../services/book-service/book";
import {BookService} from "../../services/book-service/book.service";
import {MatDialog} from "@angular/material/dialog";
import {EditGenreComponent} from "../../edit/edit-genre/edit-genre.component";

@Component({
  selector: 'app-genre-detail',
  templateUrl: './genre-detail.component.html',
  styleUrl: './genre-detail.component.css'
})
export class GenreDetailComponent implements OnInit {
  genre: Genre;
  books: Book[];

  constructor(private genreService: GenreService,
              private bookService: BookService,
              private dialog: MatDialog,
              private router: Router,
              private route: ActivatedRoute) {  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params =>{
      const id = params.get('id');
      if (id) {
        console.log('Поиск жанра по id :: ' + id);
        this.getGenre(+id);
        this.getGenreBooks(+id);
      } else {
        console.error('Жанр не найден по id :: ' + id);
      }
    });
  }

  private getGenre(id: number): void {
    this.genreService.getGenreById(id).subscribe(
      (response: Genre) => {
        this.genre = response;
        console.log('Найден жанр для просмотра деталей', response);
      },
      (error) => {
        console.error('Ошибка при поиске жанра по id :: ' + id, error);
      }
    );
  }

  editGenre(genre: Genre): void {
    console.log('Редактирование жанра', genre);
    const dialogRef = this.dialog.open(EditGenreComponent, {
      data: {genre: genre}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог редактирования жанра закрыт');
      this.getGenre(genre.id);
    });
  }

  deleteGenre(genreId: number): void {
    console.log('Удаление жанра по id :: ' + genreId);
    this.genreService.deleteGenre(genreId).subscribe({
      next: () => {
        console.log('Удален жанр по id :: ' + genreId);
        this.router.navigate(['/genres']);
      },
      error: err => console.error('Ошибка при удалении жанра по id :: ' + genreId, err)
    });
  }

  getGenreBooks(genreId: number): void {
    console.log('Получение списка книг жанра :: ' + genreId);
    this.bookService.getBooksByGenreId(genreId).subscribe(
      books => {
        this.books = books;
        console.log('Получены книги жанра :: ' + genreId, this.books);
      },
      error => {
        console.error('Ошибка при поиске книг жанра :: ' + genreId, error);
      }
    );
  }

  viewBookDetails(bookId: number): void {
    console.log('Просмотр деталей книги по id :: ' + bookId);
    this.router.navigate(['/books', bookId]);
  }

  viewAuthorDetails(authorId: number): void {
    console.log('Просмотр автора по id :: ' + authorId);
    this.router.navigate(['/authors', authorId]);
  }

}
