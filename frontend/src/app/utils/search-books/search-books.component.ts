import {Component, OnInit} from '@angular/core';
import {BookService} from "../../services/book-service/book.service";
import {SearchService} from "../../services/search-service/search.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-books',
  templateUrl: './search-books.component.html',
  styleUrl: './search-books.component.css'
})
export class SearchBooksComponent implements OnInit {
  searchQuery: string = '';
  showAdvancedSearch: boolean = false;
  title: string = '';
  authorName: string = '';
  isbn: string = '';
  year: string = '';
  genreName: string = '';
  minPages: string = '';
  maxPages: string = '';

  constructor(private bookService: BookService,
              private searchService: SearchService,
              private router: Router) {  }

  ngOnInit(): void {
  }

  toggleAdvancedSearch(): void {
    this.showAdvancedSearch = !this.showAdvancedSearch;
  }

  performSimpleSearch(): void {
    if (!this.searchQuery) {
      console.log('Поисковой запрос пуст');
      return;
    }
    this.bookService.searchBooksByTitleContains(this.searchQuery).subscribe(books => {
      console.log('Результаты поиска по части названия', books);
      this.searchService.updateSearchResults(books);
      this.router.navigate(['/search-results']);
    });
  }

  performAdvancedSearch(): void {
    const searchParams: { [key: string]: any } = {
      title: this.title,
      authorName: this.authorName,
      isbn: this.isbn,
      genreName: this.genreName,
      year: this.year ? parseInt(this.year, 10) : undefined,
      minPages: this.minPages ? parseInt(this.minPages, 10) : undefined,
      maxPages: this.maxPages ? parseInt(this.maxPages, 10) : undefined
    };

    console.log('SearchParams: ' + searchParams);
    const filteredParams = Object.entries(searchParams).reduce((acc: { [key: string]: any }, [key, value]) => {
      if (value) acc[key] = value;
      return acc;
    }, {});

    this.bookService.searchBooks(filteredParams).subscribe(results => {
      console.log('Результаты поиска по параметрам', filteredParams, results);
      this.searchService.updateSearchResults(results);
      this.router.navigate(['/search-results']);
    });
  }

}
