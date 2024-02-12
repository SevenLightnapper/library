import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {MatDialogModule} from "@angular/material/dialog";
import {BookService} from "./services/book-service/book.service";
import {HttpClientModule} from "@angular/common/http";
import {RouterLink, RouterOutlet} from "@angular/router";
import {AppRoutingModule} from "./app.routes";
import {BookDetailComponent} from "./detail/book-detail/book-detail.component";
import {HomeComponent} from "./home/home.component";
import {SearchBooksComponent} from "./utils/search-books/search-books.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SearchResultsComponent} from "./utils/search-results/search-results.component";
import {SearchService} from "./services/search-service/search.service";
import {AddBookComponent} from "./add/add-book/add-book.component";
import {GenreService} from "./services/genre-service/genre.service";
import {AddGenreComponent} from "./add/add-genre/add-genre.component";
import {EditBookComponent} from "./edit/edit-book/edit-book.component";
import {BooksComponent} from "./main/books/books.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {GenresComponent} from "./main/genres/genres.component";
import {EditGenreComponent} from "./edit/edit-genre/edit-genre.component";
import {AuthorsComponent} from "./main/authors/authors.component";
import {AddAuthorComponent} from "./add/add-author/add-author.component";
import {EditAuthorComponent} from "./edit/edit-author/edit-author.component";
import {AuthorService} from "./services/author-service/author.service";
import {CommentsComponent} from "./main/comments/comments.component";
import {CommentService} from "./services/comment-service/comment.service";
import {MatCardModule} from "@angular/material/card";
import {AuthorDetailComponent} from "./detail/author-detail/author-detail.component";
import {GenreDetailComponent} from "./detail/genre-detail/genre-detail.component";
import {RegisterComponent} from "./main/users/register/register.component";
import {LoginComponent} from "./main/users/login/login.component";
import {AuthService} from "./services/auth/auth.service";
import {UserService} from "./services/user-service/user.service";
import {UserProfileComponent} from "./main/users/user-profile/user-profile.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    // Book
    BooksComponent,
    EditBookComponent,
    BookDetailComponent,
    SearchBooksComponent,
    SearchResultsComponent,
    AddBookComponent,
    // Genre
    AddGenreComponent,
    GenresComponent,
    EditGenreComponent,
    GenreDetailComponent,
    // Author
    AuthorsComponent,
    AddAuthorComponent,
    EditAuthorComponent,
    AuthorDetailComponent,
    // Comment
    CommentsComponent,
    // User
    RegisterComponent,
    LoginComponent,
    UserProfileComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    HttpClientModule,
    RouterLink,
    RouterOutlet,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    // Mat Modules
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
  ],
  providers: [
    BookService,
    SearchService,
    GenreService,
    AuthorService,
    CommentService,
    AuthService,
    UserService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {  }
