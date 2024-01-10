import {Component, OnInit} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {matLogOut} from '@ng-icons/material-icons/baseline';
import {Router} from "@angular/router";
import {AuthService} from "../../../core/services/auth/auth.service";
import {Note} from "../../../core/data/note/note";
import {NoteService} from "../../../core/services/note/note.service";
import {MatGridListModule} from "@angular/material/grid-list";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    NgIconComponent,
    MatGridListModule,
    NgForOf
  ],
  providers: [provideIcons({matLogOut})],
  templateUrl: './notes.component.html',
  styleUrl: './notes.component.scss'
})
export class NotesComponent implements OnInit {

  rowspan = 1;
  colspan = 1;
  notes : Note[] = [];

  constructor(private router: Router,
              private authService: AuthService,
              private noteService: NoteService) {
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['auth/login']);
  }

  ngOnInit(): void {
    this.noteService.getAllNotes().subscribe({
      next: (notes) => this.notes = notes,
      error: (error) => this.router.navigate(['auth/login'])
    });
  }

}
