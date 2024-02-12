import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Book} from "../book-service/book";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private searchResultsSource = new BehaviorSubject<Book[]>([]);
  currentSearchResults = this.searchResultsSource.asObservable();

  constructor() { }

  updateSearchResults(books: Book[]) {
    console.log('Обновляем результаты поиска', books);
    this.searchResultsSource.next(books);
  }

}
