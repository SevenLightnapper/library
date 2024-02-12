import { Component } from '@angular/core';
import {AuthService} from "../../../services/auth/auth.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  model: any = {}

  constructor(private authService: AuthService,
              private dialogRef: MatDialogRef<RegisterComponent>) {  }

  register(): void {
    console.log('Регистрация пользователя')
    this.authService.register(this.model).subscribe(data => {
      console.log('Регистрация прошла успешно', data);
      this.dialogRef.close();
    }, error => {
      console.error('Ошибка регистрации', error);
      this.dialogRef.close();
    });
  }

}
