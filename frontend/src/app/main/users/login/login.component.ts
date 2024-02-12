import {Component} from '@angular/core';
import {AuthService} from "../../../services/auth/auth.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  model: any = {};

  constructor(private authService: AuthService,
              private dialogRef: MatDialogRef<LoginComponent>) {  }

  login() {
    console.log('Аутентификация пользователя')
    this.authService.login(this.model.username, this.model.password).subscribe(success => {
      if (success) {
        console.log('Вход в аккаунт выполнен успешно');
        // Если аутентификация прошла успешно, перенаправляем пользователя на домашнюю страницу
        this.dialogRef.close();
      } else {
        // Можно добавить обработку случая, когда аутентификация не удалась (неверный логин/пароль и т.д.)
        alert('Аутентификация не удалась');
        this.dialogRef.close();
      }
    });
  }

}
