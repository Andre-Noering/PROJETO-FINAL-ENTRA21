import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {  User, Venda, Pessoa } from '../app.module';
import { AuthenticationService } from '../helpers/auth.service';
import { Router } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class VendaService {

    user: User | null = null;
    constructor(
    private http: HttpClient,
    private router:Router,
    private authenticationService: AuthenticationService) { 
    this.authenticationService.user.subscribe(x => this.user = x);
    }

    getAll(razao_social:string) {
        return this.http.get<Venda[]>(`/${razao_social}/vendas`);
    }

    getValor(id:number) {
        return this.http.get<number>(`/vendas/${id}`);
    }

    getVendedor(id:number) {
        return this.http.get<Pessoa>(`/venda/vendedor/${id}`)
    }

    getCliente(id:number) {
        return this.http.get<Pessoa>(`/venda/cliente/${id}`)
    }

    add(venda:Venda){
        this.http.post(`/venda/adicionar`, venda).subscribe(
            resultado => {
              console.log(resultado);
            },
            erro => {
              if(erro.status == 400) {
                console.log(erro);
              }
            }
          );
    }
    
}