import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "./author";
import {AuthorDto} from "./authorDto";
import {authorsUrl} from "../../app.config";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiServerUrl = authorsUrl;

  constructor(private http: HttpClient) {  }

  // список всех авторов
  getAllAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(this.apiServerUrl);
  }

  // добавить нового автора
  createAuthor(author: AuthorDto): Observable<Author> {
    return this.http.post<Author>(this.apiServerUrl, author);
  }

  // найти автора по Id
  getAuthorById(id: number): Observable<Author> {
    return this.http.get<Author>(`${this.apiServerUrl}/${id}`);
  }

  // обновить автора
  updateAuthor(id: number, author: AuthorDto): Observable<Author> {
    return this.http.put<Author>(`${this.apiServerUrl}/${id}`, author);
  }

  // удалить автора
  deleteAuthor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/${id}`);
  }

}
