import { Injectable } from '@angular/core';
import {usersUrl} from "../../app.config";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../main/users/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiServerUrl = usersUrl;

  constructor(private http: HttpClient) { }

  getUserProfile(): Observable<User> {
    const token = localStorage.getItem('token'); // Получаем токен из localStorage
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` // Добавляем токен в заголовок
    });
    return this.http.get<User>(`${this.apiServerUrl}/profile`, {headers: headers});
  }

  // Методы для обновления профиля, изменения пароля и т.д.

}
