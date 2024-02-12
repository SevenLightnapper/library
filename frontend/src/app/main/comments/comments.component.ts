import {Component, Input, OnInit} from '@angular/core';
import {CommentM} from "../../services/comment-service/commentM";
import {CommentService} from "../../services/comment-service/comment.service";
import {CommentDto} from "../../services/comment-service/commentDto";
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrl: './comments.component.css'
})
export class CommentsComponent implements OnInit {
  @Input() bookId: number;
  comments: CommentM[];
  newCommentText: string = '';

  constructor(private commentService: CommentService,
              private router: Router,
              private authService: AuthService) {  }

  ngOnInit() {
    this.loadComments();
  }

  loadComments(): void {
    console.log('Загрузка комментариев книги')
    this.commentService.getCommentsByBookId(this.bookId).subscribe(comments => {
      this.comments = comments;
      console.log('Загруженные комментарии', this.comments);
    });
  }

  addComment(): void {
    if (this.authService.isLoggedIn()) {
      console.log('Добавление комментария')
      const comment: CommentDto = {text: this.newCommentText, bookId: this.bookId};
      console.log('Данные комментария для добавления', comment);
      this.commentService.addComment(comment).subscribe(
        newComment => {
          this.comments.push(newComment);
          console.log('Комментарий добавлен', newComment);
          this.newCommentText = '';
          this.loadComments();
        },
        error => console.error('Ошибка при добавлении комментария', error));
    } else {
      console.log('Пользователь не аутентифицирован');
      this.router.navigate(['/auth/signup']);
    }
  }

}
