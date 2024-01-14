import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Note} from "../../data/note/note";
import {ApiUrl} from "../../enums/api-url";
import {environment} from "../../../../environments/environment";
import {NoteRequest} from "../../data/note/note-request";
import {catchError, tap, throwError} from "rxjs";
import {NoteList} from "../../data/note/note-list";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  constructor(private http: HttpClient) { }


  getAllNotes(pageNumber: number) {
    return this.http.get<NoteList>(`${environment.API_URL}${ApiUrl.FIND_ALL_NOTES}`, {params: {pageNumber: pageNumber}});
  }

  findByText(text: string, pageNumber: number) {
    return this.http.get<NoteList>(`${environment.API_URL}${ApiUrl.FIND_BY_TEXT}`, {params: {text: text, pageNumber: pageNumber}});
  }

  createNote(title: string, text: string) {
    let noteRequest: NoteRequest = {title: title, text: text};
    return this.http.post<Note>(`${environment.API_URL}${ApiUrl.CREATE_NOTE}`, noteRequest).pipe(
      tap(),
      catchError((error: any) => {
        let errMsg = `An error occurred while creating note: ${error.error.message}`;
        return throwError(() => new Error(errMsg))
      })
    );
  }

  updateNote(id: number, title: string, text: string) {
    let noteRequest: NoteRequest = {title: title, text: text};
    return this.http.put<Note>(`${environment.API_URL}${ApiUrl.UPDATE_NOTE}`, noteRequest, {params: {id: id}}).pipe(
      tap(),
      catchError((error: any) => {
        let errMsg = `An error occurred while updating note: ${error.error.message}`;
        return throwError(() => new Error(errMsg))
      })
    );
}

}
