import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Item, Loja } from 'src/app/app.module';
import { ItemService } from 'src/app/services/item.service';
import { LojaService } from 'src/app/services/loja.service';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent {
  loja:Loja | null = null;

  formItem = this.formBuilder.group({
    nome:['', Validators.required],
    valor:[0, Validators.required],
    qtdeEstoque:[0, Validators.required],
    qtdeAlertaEstoque:[0, Validators.required],
    idLoja:[0, Validators.required]
  });

  constructor(
    private formBuilder:FormBuilder,
    private itemService:ItemService,
    private lojaService:LojaService,
    private route: ActivatedRoute,
    private router: Router,
  ) { 
    
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.lojaService.getByRazaoSocial(params['razao_social']).subscribe(resultado => {
        this.loja = resultado;
        this.formItem.get("idLoja")?.patchValue(this.loja.id);
      },
      erro => {
        if(erro.status == 400) {
          console.log(erro);
        }
      });
    })
  }
  addItem(){
    this.itemService.add(this.formItem.value as Item);
    this.router.navigate([`/lojas/${this.loja?.razao_social ?? ''}/estoque`])
  }
}
