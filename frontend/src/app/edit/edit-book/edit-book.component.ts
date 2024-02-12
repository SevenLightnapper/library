import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BookService} from "../../services/book-service/book.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GenreService} from "../../services/genre-service/genre.service";
import {Genre} from "../../services/genre-service/genre";
import {isbnValidator} from "../../utils/validators/isbn-validator";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Book} from "../../services/book-service/book";
import {Author} from "../../services/author-service/author";
import {AuthorService} from "../../services/author-service/author.service";
import {BookDto} from "../../services/book-service/bookDto";

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrl: './edit-book.component.css'
})
export class EditBookComponent implements OnInit {
  editBookForm: FormGroup;
  allGenres: Genre[];
  allAuthors: Author[];

  constructor(private formBuilder: FormBuilder,
              private bookService: BookService,
              private genreService: GenreService,
              private authorService: AuthorService,
              private router: Router,
              private route: ActivatedRoute,
              private dialogRef: MatDialogRef<EditBookComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {book: Book}) {
    this.allGenres = [];
  }

  ngOnInit(): void {
    this.loadGenres();
    this.loadAuthors();
    this.initForm();
  }

  loadGenres(): void {
    this.genreService.getAllGenres().subscribe({
      next: genres => {
        this.allGenres = genres;
        console.log('Загруженные жанры:', genres);
      },
      error: err => console.error('Ошибка при загрузке жанров', err)
    });
  }

  loadAuthors(): void {
    this.authorService.getAllAuthors().subscribe({
      next: authors => {
        this.allAuthors = authors;
        console.log('Загруженные авторы:', authors);
      },
      error: err => console.error('Ошибка при загрузке авторов', err)
    });
  }

  initForm(): void {
    console.log('Инициализация формы редактирования книги');
    console.log('Текущий жанр книги', this.data.book.genre);
    this.editBookForm = this.formBuilder.group({
      title: [this.data.book.title, Validators.required],
      author: [this.data.book.author || null, Validators.required],
      isbn: [this.data.book.isbn, [Validators.required, isbnValidator]],
      summary: [this.data.book.summary],
      genre: [this.data.book.genre || null, Validators.required],
      year: [this.data.book.year],
      numberOfPages: [this.data.book.numberOfPages]
    });
    console.log('Данные формы редактирования книги', this.editBookForm.value);
  }

  onSubmit(): void {
    if (this.editBookForm.valid) {
      const {id, title, author, isbn, summary, genre, year, numberOfPages} = this.editBookForm.value;
      const bookData: BookDto = {
        id: Number(id),
        title: String(title),
        authorId: Number(author.id),
        isbn: String(isbn),
        summary: String(summary),
        genreId: Number(genre.id),
        year: Number(year),
        numberOfPages: Number(numberOfPages)
      };
      console.log('ID обновления', this.data.book.id);
      console.log('Данные обновления', bookData);
      this.bookService.updateBook(this.data.book.id, bookData).subscribe({
        next: updatedBook => {
          console.log('Данные книги для обновления', updatedBook);
          this.dialogRef.close(updatedBook);
        },
        error: error => {
          console.error('Ошибка при обновлении книги', error);
          this.dialogRef.close();
        }
      });
    }
  }

  onCancel(): void {
    console.log('Закрываем окно обновления книги');
    this.dialogRef.close();
  }

  compareGenres(genre1: any, genre2: any): boolean {
    return genre1 && genre2 ? genre1.id === genre2.id : genre1 === genre2;
  }

  compareAuthors(author1: any, author2: any): boolean {
    return author1 && author2 ? author1.id === author2.id : author1 === author2;
  }

}
