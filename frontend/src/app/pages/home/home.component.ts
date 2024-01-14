import {Component, OnInit} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {matLogOut} from '@ng-icons/material-icons/baseline';
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {AuthService} from "../../core/services/auth/auth.service";
import {Note} from "../../core/data/note/note";
import {NoteService} from "../../core/services/note/note.service";
import {MatGridListModule} from "@angular/material/grid-list";
import {DatePipe, NgForOf} from "@angular/common";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    NgIconComponent,
    MatGridListModule,
    NgForOf,
    MatInputModule,
    FormsModule,
    RouterOutlet,
    RouterLink
  ],
  providers: [provideIcons({matLogOut}), DatePipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private router: Router,
              private authService: AuthService,
              private noteService: NoteService,
              private datePipe: DatePipe) {
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['auth/login']);
  }
}
