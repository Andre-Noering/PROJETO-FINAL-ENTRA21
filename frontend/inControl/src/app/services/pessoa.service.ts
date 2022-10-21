import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Loja, User, Item, Funcionario, Pessoa } from '../app.module';
import { AuthenticationService } from '../helpers/auth.service';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class PessoaService {

    user: User | null = null;
    constructor(
    private http: HttpClient,
    private router:Router,
    private authenticationService: AuthenticationService) { 
    this.authenticationService.user.subscribe(x => this.user = x);
    }

    getClientes(razao_social:string){
        return this.http.get<Pessoa[]>(`/${razao_social}/pessoas/clientes`)
    }
}