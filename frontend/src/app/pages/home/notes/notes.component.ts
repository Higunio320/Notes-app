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
import {DatePipe, NgForOf} from "@angular/common";

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
  providers: [provideIcons({matLogOut}), DatePipe],
  templateUrl: './notes.component.html',
  styleUrl: './notes.component.scss'
})
export class NotesComponent implements OnInit {

  rowspan = 1;
  colspan = 1;
  notes : Note[] = [];

  constructor(private router: Router,
              private authService: AuthService,
              private noteService: NoteService,
              private datePipe: DatePipe) {
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['auth/login']);
  }

  ngOnInit(): void {
    this.noteService.getAllNotes().subscribe({
      next: (notes: Note[]) =>  {
        this.notes = notes;
        this.notes.forEach((note) => {
          note.lastEdit = <string>this.datePipe.transform(note.lastEdit, 'dd-MM-yyyy HH:mm');
        })},
      error: () => this.router.navigate(['auth/login'])
    });
  }

}
