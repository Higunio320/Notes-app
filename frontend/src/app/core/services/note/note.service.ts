import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Note} from "../../data/note/note";
import {ApiUrl} from "../../enums/api-url";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  constructor(private http: HttpClient) { }


  getAllNotes() {
    return this.http.get<Note[]>(`${environment.API_URL}${ApiUrl.FIND_ALL_NOTES}`);
  }

  findByText(text: string) {
    return this.http.get<Note[]>(`${environment.API_URL}${ApiUrl.FIND_BY_TEXT}`, {params: {text: text}})
  }

}
