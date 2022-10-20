import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Loja, User, Item } from '../app.module';
import { AuthenticationService } from '../helpers/auth.service';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class ItemService {

    user: User | null = null;
    constructor(
    private http: HttpClient,
    private router:Router,
    private authenticationService: AuthenticationService) { 
    this.authenticationService.user.subscribe(x => this.user = x);
    }

    getAll(razao_social:string) {
        return this.http.get<Item[]>(`/${razao_social}/itens`);
    }
    add(razao_social:string, item:Item){
        this.http.post(`/${razao_social}/itens/adicionar`, item).subscribe(
            resultado => {
              this.router.navigate([`/estoque`])
            },
            erro => {
              if(erro.status == 400) {
                console.log(erro);
              }
            }
          );
    }
}