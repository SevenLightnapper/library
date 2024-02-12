import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthorService} from "../../services/author-service/author.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Author} from "../../services/author-service/author";

@Component({
  selector: 'app-edit-author',
  templateUrl: './edit-author.component.html',
  styleUrl: './edit-author.component.css'
})
export class EditAuthorComponent implements OnInit {
  editAuthorForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authorService: AuthorService,
              private router: Router,
              private route: ActivatedRoute,
              private dialogRef: MatDialogRef<EditAuthorComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {author: Author}) {  }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    console.log('Инициализация формы редактирования автора');
    this.editAuthorForm = this.formBuilder.group({
      name: [this.data.author.name, Validators.required],
      bio: [this.data.author.bio]
    });
    console.log('Данные формы редактирования автора', this.editAuthorForm.value);
  }

  onSubmit(): void {
    if (this.editAuthorForm.valid) {
      const authorData = {
        ...this.editAuthorForm.value
      };
      console.log('ID редактированного автора', this.data.author.id);
      console.log('Данные редактирования', authorData);
      this.authorService.updateAuthor(this.data.author.id, authorData).subscribe({
        next: updatedAuthor => {
          console.log('Данные автора для редактирования', updatedAuthor);
          this.dialogRef.close(updatedAuthor);
        },
        error: err => {
          console.error('Ошибка при редактировании автора', err);
          this.dialogRef.close();
        }
      });
    }
  }

  onCancel(): void {
    console.log('Закрываем окно редактирования автора');
    this.dialogRef.close();
  }

}
