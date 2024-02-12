import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BookService} from "../../services/book-service/book.service";
import {GenreService} from "../../services/genre-service/genre.service";
import {Genre} from "../../services/genre-service/genre";
import {isbnValidator} from "../../utils/validators/isbn-validator";
import {MatDialogRef} from "@angular/material/dialog";
import {Author} from "../../services/author-service/author";
import {AuthorService} from "../../services/author-service/author.service";

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrl: './add-book.component.css'
})
export class AddBookComponent implements OnInit {
  addBookForm: FormGroup;
  allGenres: Genre[];
  allAuthors: Author[];

  constructor(private formBuilder: FormBuilder,
              private bookService: BookService,
              private genreService: GenreService,
              private authorService: AuthorService,
              private dialogRef: MatDialogRef<AddBookComponent>) {
    this.allGenres = [];
    this.allAuthors = [];
  }

  onCancel(): void {
    console.log('Закрываем окно добавления книги');
    this.dialogRef.close();
  }

  ngOnInit() {
    this.loadGenres();
    this.loadAuthors();
    this.initializeForm();
  }

  initializeForm(): void {
    console.log('Инициализация формы добавления книги');
    this.addBookForm = this.formBuilder.group({
      title: ['', Validators.required],
      authorId: ['', Validators.required],
      isbn: ['', [Validators.required, isbnValidator()]],
      summary: [''],
      genreId: ['', Validators.required],
      year: [''],
      numberOfPages: ['']
    });
    console.log('Данные формы добавления книги', this.addBookForm.value);
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

  onSubmit() {
    if (this.addBookForm.valid) {
      const bookData = {
        ...this.addBookForm.value
      };
      this.bookService.createBook(bookData).subscribe({
        next: (createdBook) => {
          console.log('Книга добавлена', createdBook);
          this.dialogRef.close();
        },
        error: (error) => {
          console.error('Ошибка при добавлении книги', error);
          this.dialogRef.close();
        }
      });
    }
  }

}
