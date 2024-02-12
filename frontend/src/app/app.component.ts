import {Component, HostListener, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {LoginComponent} from "./main/users/login/login.component";
import {RegisterComponent} from "./main/users/register/register.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const clickedInsideMenu = (event.target as HTMLElement).closest('.header') ||
      (event.target as HTMLElement).classList.contains('hamburger-menu');

    if (!clickedInsideMenu) {
      this.isMenuOpen = false;
    }
  }

  constructor(private dialog: MatDialog) {  }

  ngOnInit(): void {  }

  signin(): void {
    console.log('Логин');
    const dialogRef = this.dialog.open(LoginComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог логина закрыт');
    });
  }

  signup() : void {
    console.log('Регистрация');
    const dialogRef = this.dialog.open(RegisterComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log('Диалог регистрации закрыт');
    });
  }

}
