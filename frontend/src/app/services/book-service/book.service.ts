import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BookDto} from "./bookDto";
import {Book} from "./book";
import {booksUrl} from "../../app.config";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiServerUrl = booksUrl;

  constructor(private http: HttpClient) { }

  // список всех книг
  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiServerUrl);
  }

  // найти книгу по ID
  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiServerUrl}/${id}`);
  }

  // добавить новую книгу
  createBook(book: BookDto): Observable<Book> {
    return this.http.post<Book>(this.apiServerUrl, book);
  }

  // обновить книгу
  updateBook(id: number, book: BookDto): Observable<Book> {
    return this.http.put<Book>(`${this.apiServerUrl}/${id}`, book);
  }

  // удалить книгу
  deleteBook(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/${id}`);
  }

  // искать книги
  searchBooks(params: any): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiServerUrl}/search`, {params});
  }

  // найти книги по строке в названии
  searchBooksByTitleContains(title: string): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiServerUrl}/search/${title}`);
  }

  // найти книги по ID автора
  getBooksByAuthorId(authorId: number) {
    return this.http.get<Book[]>(`${this.apiServerUrl}/author/${authorId}`);
  }

  // найти книги по ID жанра
  getBooksByGenreId(genreId: number) {
    return this.http.get<Book[]>(`${this.apiServerUrl}/genre/${genreId}`);
  }

}
