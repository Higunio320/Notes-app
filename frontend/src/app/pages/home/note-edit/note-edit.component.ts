import {Component, OnInit} from '@angular/core';
import {Note} from "../../../core/data/note/note";
import {Router, RouterLink} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NoteService} from "../../../core/services/note/note.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-note-edit',
  standalone: true,
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    NgIf,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './note-edit.component.html',
  styleUrl: './note-edit.component.scss'
})
export class NoteEditComponent implements OnInit {
  note: Note;

  noteGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    text: new FormControl('', Validators.required)
  });

  constructor(private router: Router,
              private noteService: NoteService,
              private snackBar: MatSnackBar) {
    this.note = history.state.note;
  }

  ngOnInit() {
    if(!this.note) {
      this.router.navigate(['/home'])
    }

    this.noteGroup.get('title')!.setValue(this.note.title);
    this.noteGroup.get('text')!.setValue(this.note.text);
  }

  updateNote() {
    if(this.noteGroup.invalid) {
      return;
    }

    const title = this.noteGroup.get('title')!.value;
    const text = this.noteGroup.get('text')!.value;

    if(!title || !text) {
      return;
    }

    this.noteService.updateNote(this.note.id, title, text).subscribe({
      next: () => this.router.navigate(['home/notes/list']),
      error: (error) => this.snackBar.open(error.message, 'Close')
    });
  }

}
