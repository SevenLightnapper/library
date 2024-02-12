import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {GenreService} from "../../services/genre-service/genre.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-genre',
  templateUrl: './add-genre.component.html',
  styleUrl: './add-genre.component.css'
})
export class AddGenreComponent implements OnInit {
  addGenreForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private genreService: GenreService,
              private dialogRef: MatDialogRef<AddGenreComponent>) {  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    console.log('Инициализация формы добавления жанра');
    this.addGenreForm = this.formBuilder.group({
      name: ['', Validators.required]
    });
    console.log('Данные формы добавления жанра', this.addGenreForm.value);
  }

  onSubmit(): void {
    if (this.addGenreForm.valid) {
      const genreData = {
        ...this.addGenreForm.value,
        books: []
      }
      this.genreService.addGenre(genreData).subscribe({
        next: (createdGenre) => {
          console.log('Жанр добавлен', createdGenre);
          this.dialogRef.close();
        },
        error: (error) => {
          console.error('Ошибка при добавлении жанра', error);
          this.dialogRef.close();
        }
      });
    }
  }

  onCancel(): void {
    console.log('Закрываем окно добавления жанра');
    this.dialogRef.close();
  }

}
