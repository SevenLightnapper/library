import {Book} from "../book-service/book";

export interface CommentM {
  id: number;
  text: string;
  book: Book;
  createdAt?: Date;
}
