import {Component, OnInit} from '@angular/core';
import {User} from "../user.model";
import {UserService} from "../../../services/user-service/user.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {
  user: User;

  constructor(private userService: UserService) {  }

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe({
      next: data => {
        this.user = data;
      },
      error: err => console.error(err)
    });
  }

}
