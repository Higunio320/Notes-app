import { Component } from '@angular/core';
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NoteService} from "../../../core/services/note/note.service";
import {HttpClient} from "@angular/common/http";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-note-add',
  standalone: true,
  imports: [
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    RouterLink
  ],
  templateUrl: './note-add.component.html',
  styleUrl: './note-add.component.scss'
})
export class NoteAddComponent {

  constructor(
    private snackBar: MatSnackBar,
    private noteService: NoteService,
    private router: Router
  ) {}

  noteGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    text: new FormControl('', Validators.required)
  });

  createNote() {
    if(this.noteGroup.invalid) {
      this.snackBar.open('Please fill in all the fields', 'Close', {duration: 5000});
      return;
    }

    const title = this.noteGroup.get('title')!.value;
    const text = this.noteGroup.get('text')!.value;

    if(!title || !text) {
      return;
    }

    this.noteService.createNote(title, text).subscribe({
      next: () => this.router.navigate(['home/notes/list']),
      error: (error) => this.snackBar.open(error.message, 'Close')
    });

  }

}
