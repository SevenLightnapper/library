import {Genre} from "../genre-service/genre";
import {Author} from "../author-service/author";

export interface Book {
  id: number;
  title: string;
  author: Author;
  isbn?: string;
  summary?: string;
  year?: number;
  numberOfPages?: number;
  genre: Genre;
}
