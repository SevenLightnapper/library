import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Genre} from "./genre";
import {GenreDto} from "./genreDto";
import {genresUrl} from "../../app.config";

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private apiServerUrl = genresUrl;

  constructor(private http: HttpClient) {  }

  getAllGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiServerUrl);
  }

  addGenre(genre: GenreDto): Observable<Genre> {
    return this.http.post<Genre>(this.apiServerUrl, genre);
  }

  updateGenre(id: number, genre: GenreDto): Observable<Genre> {
    return this.http.put<Genre>(`${this.apiServerUrl}/${id}`, genre);
  }

  deleteGenre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/${id}`);
  }

  getGenreById(id: number): Observable<Genre> {
    return this.http.get<Genre>(`${this.apiServerUrl}/${id}`);
  }

}
