import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, catchError, map, Observable, of} from "rxjs";
import {authUrl} from "../../app.config";
import {jwtDecode} from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<any>;

  apiServerUrl = authUrl;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')!));
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<any> {
    console.log('Отправляем форму логина на бэкенд');
    return this.http.post<{jwt: string}>(this.apiServerUrl + 'signin', { username, password })
      .pipe(
        map(response => {
          // Если в ответе есть токен, сохраняем его в localStorage
          console.log('1')
          localStorage.setItem('token', response.jwt);
          // токен
          const decodedToken = jwtDecode(response.jwt);

          localStorage.setItem('currentUser', JSON.stringify(decodedToken));

          // обновляем текущее состояние пользователя
          this.currentUserSubject.next(decodedToken);

          console.log('Успешный вход');
          return response; // Успешный вход
        }),
        catchError(error => {
          // Обработка ошибок, например, показ сообщения пользователю
          console.error('Ошибка аутентификации', error);
          return of(false); // В случае ошибки возвращаем false
        })
      );
  }

  // Метод для проверки, аутентифицирован ли пользователь
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  // Метод для выхода пользователя
  logout(): void {
    localStorage.removeItem('token');
  }

  // метод для регистрации пользователя
  register(model: any) {
    console.log('Отправляем форму регистрации на бэкенд');
    return this.http.post(this.apiServerUrl + 'signup', model);
  }

  // Метод для удаления пользователя
  delete(): void {
    console.log('Удаление пользователя');

  }

}
