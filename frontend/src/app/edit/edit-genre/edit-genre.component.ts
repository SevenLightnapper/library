import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {GenreService} from "../../services/genre-service/genre.service";
import {Genre} from "../../services/genre-service/genre";

@Component({
  selector: 'app-edit-genre',
  templateUrl: './edit-genre.component.html',
  styleUrl: './edit-genre.component.css'
})
export class EditGenreComponent implements OnInit {
  editGenreForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private genreService: GenreService,
              private router: Router,
              private route: ActivatedRoute,
              private dialogRef: MatDialogRef<EditGenreComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {genre: Genre}) {  }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    console.log('Инициализация формы редактирования жанра');
    this.editGenreForm = this.formBuilder.group({
      name: [this.data.genre.name, Validators.required]
    });
    console.log('Данные формы редактирования жанра', this.editGenreForm.value);
  }

  onSubmit(): void {
    if (this.editGenreForm.valid) {
      const genreData = {
        ...this.editGenreForm.value
      };
      console.log('ID редактированного жанра', this.data.genre.id);
      console.log('Данные редактирования', genreData);
      this.genreService.updateGenre(this.data.genre.id, genreData).subscribe({
        next: updatedGenre => {
          console.log('Данные жанра для редактирования', updatedGenre);
          this.dialogRef.close(updatedGenre);
        },
        error: err => {
          console.error('Ошибка при редактировании жанра', err);
          this.dialogRef.close();
        }
      });
    }
  }

  onCancel(): void {
    console.log('Закрываем окно редактирования жанра');
    this.dialogRef.close();
  }

}
