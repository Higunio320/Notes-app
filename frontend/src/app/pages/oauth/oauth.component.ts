import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route, Router} from '@angular/router';
import {StorageService} from "../../core/services/storage/storage.service";

@Component({
  selector: 'app-oauth',
  standalone: true,
  imports: [],
  templateUrl: './oauth.component.html',
  styleUrl: './oauth.component.scss'
})
export class OauthComponent implements OnInit{

  constructor(private route: ActivatedRoute,
              private storageService: StorageService,
              private router: Router) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      let token = params['token'];
      if(token) {
        this.storageService.saveToken(token);
        this.router.navigate(['/home']);
      } else {
        this.router.navigate([''])
      }
    });
  }

}
