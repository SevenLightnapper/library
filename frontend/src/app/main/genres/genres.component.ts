import {Component, OnInit} from '@angular/core';
import {Genre} from "../../services/genre-service/genre";
import {GenreService} from "../../services/genre-service/genre.service";
import {MatDialog} from "@angular/material/dialog";
import {AddGenreComponent} from "../../add/add-genre/add-genre.component";
import {EditGenreComponent} from "../../edit/edit-genre/edit-genre.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-genres',
  templateUrl: './genres.component.html',
  styleUrl: './genres.component.css'
})
export class GenresComponent implements OnInit {
  genres: Genre[] = [];

  constructor(private genreService: GenreService,
              private router: Router,
              private dialog: MatDialog) {  }

  ngOnInit() {
    this.getAllGenres();
  }

  getAllGenres(): void {
    this.genreService.getAllGenres().subscribe({
      next: data => {
        this.genres = data;
        console.log('Все жанры', this.genres);
      },
      error: err => console.error('Ошибка при загрузке списка жанров', err)
    });
  }

  addGenre(): void {
    console.log('Добавление жанра')
    const dialogRef = this.dialog.open(AddGenreComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог добавления жанра закрыт');
      this.getAllGenres();
    });
  }

  editGenre(genre: Genre): void {
    console.log('Редактирование жанра', genre);
    const dialogRef = this.dialog.open(EditGenreComponent, {
      data: {genre: genre}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог обновления жанра закрыт');
      this.getAllGenres();
    });
  }

  deleteGenre(genreId: number): void {
    console.log('Удаление жанра по id :: ' + genreId);
    this.genreService.deleteGenre(genreId).subscribe({
      next: () => {
        console.log('Удален жанр по id :: ' + genreId);
        this.getAllGenres();
      },
      error: err => console.error('Ошибка при удалении жанра по id :: ' + genreId, err)
    });
  }

  viewGenreDetails(genreId: number): void {
    console.log('Просмотр деталей жанра по id :: ' + genreId);
    this.router.navigate(['/genres', genreId]);
  }

}
