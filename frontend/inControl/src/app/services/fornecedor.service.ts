import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Fornecedor, Loja, User } from '../app.module';
import { AuthenticationService } from '../helpers/auth.service';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class FornecedorService {
    user: User | null = null;
    constructor(
    private http: HttpClient,
    private router:Router,
    private authenticationService: AuthenticationService) {
    this.authenticationService.user.subscribe(x => this.user = x);
    }

    getAll(razao_social:string) {
        return this.http.get<Fornecedor[]>(`/${razao_social}/fornecedores`);
    }   
    getByRazaoSocial(razao_social:string){
      return this.http.get<Loja>(`${razao_social}`);
    }

    add(razao_social:string, fornecedor:Fornecedor){
        return this.http.post(`/${razao_social}/fornecedores/adicionar`, fornecedor).subscribe(
            resultado => {
            },
            erro => {
              if(erro.status == 400) {
                console.log(erro);
              }
            }
        );
    }
}