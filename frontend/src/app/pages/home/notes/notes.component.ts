import { Component } from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {matLogOut} from '@ng-icons/material-icons/baseline';

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    NgIconComponent
  ],
  providers: [provideIcons({matLogOut})],
  templateUrl: './notes.component.html',
  styleUrl: './notes.component.scss'
})
export class NotesComponent {

}
