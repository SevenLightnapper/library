export interface BookDto {
  id: number;
  title: string;
  authorId: number;
  isbn: string;
  summary?: string;
  year?: number;
  numberOfPages?: number;
  genreId: number;
}
