import { Injectable } from '@angular/core';
import {commentsUrl} from "../../app.config";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommentDto} from "./commentDto";
import {CommentM} from "./commentM";

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiServerUrl = commentsUrl;

  constructor(private http: HttpClient) {  }

  public getCommentsByBookId(bookId: number): Observable<CommentM[]> {
    return this.http.get<CommentM[]>(`${this.apiServerUrl}/${bookId}/comments`);
  }

  public addComment(/*bookId: number,*/ comment: CommentDto): Observable<CommentM> {
    return this.http.post<CommentM>(`${this.apiServerUrl}/${comment.bookId}/comments`, comment);
  }

  public updateComment(bookId: number, commentId: number, comment: CommentDto): Observable<CommentM> {
    return this.http.put<CommentM>(`${this.apiServerUrl}/${bookId}/comments/${commentId}`, comment);
  }

  public deleteComment(bookId: number, commentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/${bookId}/comments/${commentId}`);
  }

}
