import {Component, OnInit} from '@angular/core';
import {Author} from "../../services/author-service/author";
import {AuthorService} from "../../services/author-service/author.service";
import {MatDialog} from "@angular/material/dialog";
import {AddAuthorComponent} from "../../add/add-author/add-author.component";
import {EditAuthorComponent} from "../../edit/edit-author/edit-author.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrl: './authors.component.css'
})
export class AuthorsComponent implements OnInit {
  authors: Author[] = [];

  constructor(private authorService: AuthorService,
              private router: Router,
              private dialog: MatDialog) {  }

  ngOnInit() {
    this.getAllAuthors();
  }

  getAllAuthors(): void {
    this.authorService.getAllAuthors().subscribe({
      next: data => {
        console.log('Все авторы', data);
        this.authors = data;
      },
      error: err => console.error('Ошибка при загрузке списка авторов', err)
    });
  }

  addAuthor(): void {
    console.log('Добавление автора');
    const dialogRef = this.dialog.open(AddAuthorComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог добавления автора закрыт');
      this.getAllAuthors();
    });
  }

  editAuthor(author: Author): void {
    console.log('Редактирование автора', author);
    const dialogRef = this.dialog.open(EditAuthorComponent, {
      data: {author: author}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог редактирования автора закрыт');
      this.getAllAuthors();
    });
  }

  deleteAuthor(authorId: number): void {
    console.log('Удаление автора по id :: ' + authorId);
    this.authorService.deleteAuthor(authorId).subscribe({
      next: () => {
        console.log('Удален автор по id :: ' + authorId);
        this.getAllAuthors();
      },
      error: err => console.log('Ошибка при удалении автора по id :: ' + authorId, err)
    });
  }

  viewAuthorDetails(authorId: number): void {
    console.log('Просмотр страницы автора по id :: ' + authorId);
    this.router.navigate(['/authors', authorId]);
  }

}
