import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";
import {BookDetailComponent} from "./detail/book-detail/book-detail.component";
import {HomeComponent} from "./home/home.component";
import {SearchBooksComponent} from "./utils/search-books/search-books.component";
import {SearchResultsComponent} from "./utils/search-results/search-results.component";
import {BooksComponent} from "./main/books/books.component";
import {GenresComponent} from "./main/genres/genres.component";
import {AuthorsComponent} from "./main/authors/authors.component";
import {CommentsComponent} from "./main/comments/comments.component";
import {AuthorDetailComponent} from "./detail/author-detail/author-detail.component";
import {GenreDetailComponent} from "./detail/genre-detail/genre-detail.component";
import {RegisterComponent} from "./main/users/register/register.component";
import {LoginComponent} from "./main/users/login/login.component";
import {UserProfileComponent} from "./main/users/user-profile/user-profile.component";

const routes: Routes = [
  {path: '', redirectTo: "/home", pathMatch: "full"},
  {path: 'home', component: HomeComponent},
  {path: 'books', component: BooksComponent},
  {path: 'books/:id', component: BookDetailComponent},
  {path: 'books/search', component: SearchBooksComponent},
  {path: 'search-results', component: SearchResultsComponent},
  {path: 'genres', component: GenresComponent},
  {path: 'authors', component: AuthorsComponent},
  {path: 'books/:id/comments', component: CommentsComponent},
  {path: 'authors/:id', component: AuthorDetailComponent},
  {path: 'genres/:id', component: GenreDetailComponent},
  {path: 'auth/signup', component: RegisterComponent},
  {path: 'auth/signin', component: LoginComponent},
  {path: 'users/profile', component: UserProfileComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {  }
