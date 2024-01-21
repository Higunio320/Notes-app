import {ChangeDetectorRef, Component, HostListener, OnInit} from '@angular/core';
import {MatGridListModule} from "@angular/material/grid-list";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Note} from "../../../core/data/note/note";
import {Router} from "@angular/router";
import {NoteService} from "../../../core/services/note/note.service";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {NoteList} from "../../../core/data/note/note-list";

@Component({
  selector: 'app-notes-list',
  standalone: true,
  imports: [
    MatGridListModule,
    NgForOf,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule,
    MatPaginatorModule,
    NgIf
  ],
  templateUrl: './notes-list.component.html',
  styleUrl: './notes-list.component.scss'
})
export class NotesListComponent implements OnInit{

  constructor(
    private router: Router,
    private noteService: NoteService,
    private datePipe: DatePipe,
  ) {}

  rowspan = 1;
  colspan = 1;
  notes : Note[] = [];
  noteText = '';

  totalNotes = 0;
  pageNumber = 0;


  search() {
    if(!this.noteText) {
      this.ngOnInit();
      return;
    }
    this.noteService.findByText(this.noteText, this.pageNumber).subscribe({
      next: (notes: NoteList) => {
        this.notes = notes.notes;
        this.notes.forEach((note) => {
          note.lastEdit = <string>this.datePipe.transform(note.lastEdit, 'dd-MM-yyyy HH:mm');
        });
        this.totalNotes = notes.totalNotes;
      }
    })
  }

  ngOnInit(): void {
    this.loadNotes();
    this.adjustGrid(window.innerWidth);
  }

  loadNotes() {
    this.noteService.getAllNotes(this.pageNumber).subscribe({
      next: (notes: NoteList) => {
        this.notes = notes.notes;
        this.notes.forEach((note) => {
          note.lastEdit = <string>this.datePipe.transform(note.lastEdit, 'dd-MM-yyyy HH:mm');
        });
        this.totalNotes = notes.totalNotes;
      },
      error: () => this.router.navigate(['auth/login'])
    });
  }

  onPageChange(event: PageEvent) {
    this.pageNumber = event.pageIndex;
    if(this.noteText) {
      this.search();
    } else {
      this.ngOnInit();
    }
  }

  onNoteClick(note: Note) {
    this.router.navigate(['home/notes/edit'], {state: {note: note}});
  }

  @HostListener('window:resize', ['$event'])
  onResize(event : any) {
    this.adjustGrid(event.target.innerWidth);
  }

  adjustGrid(width: number) {
    if (width < 768) {
      this.colspan = 2;
    } else {
      this.colspan = 1;
    }
  }
}
