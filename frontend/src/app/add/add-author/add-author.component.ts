import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthorService} from "../../services/author-service/author.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-author',
  templateUrl: './add-author.component.html',
  styleUrl: './add-author.component.css'
})
export class AddAuthorComponent implements OnInit {
  addAuthorForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authorService: AuthorService,
              private dialogRef: MatDialogRef<AddAuthorComponent>) {  }

  ngOnInit() {
    this.initializeForm();
  }

  initializeForm(): void {
    console.log('Инициализация формы добавления автора');
    this.addAuthorForm = this.formBuilder.group({
      name: ['', Validators.required],
      bio: ['']
    });
    console.log('Данные формы добавления автора', this.addAuthorForm.value);
  }

  onSubmit() {
    if (this.addAuthorForm.valid) {
      const authorData = {
        ...this.addAuthorForm.value,
        books: []
      };
      this.authorService.createAuthor(authorData).subscribe({
        next: (createdAuthor) => {
          console.log('Автор добавлен', createdAuthor);
          this.dialogRef.close();
        },
        error: (error) => {
          console.error('Ошибка при добавлении автора', error);
          this.dialogRef.close();
        }
      });
    }
  }

  onCancel(): void {
    console.log('Закрываем окно добавления автора');
    this.dialogRef.close();
  }

}
