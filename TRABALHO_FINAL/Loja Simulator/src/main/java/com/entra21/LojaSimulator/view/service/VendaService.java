
package com.entra21.LojaSimulator.view.service;

import com.entra21.LojaSimulator.model.dto.*;
import com.entra21.LojaSimulator.model.entity.VendaEntity;
import com.entra21.LojaSimulator.view.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ItemVendaService itemVendaService;

    public VendaEntity getVenda(Long id){ //Retorna a loja com aquele ID
        return vendaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Venda não encontrada!"));
    }

    //Retorna nome, valor e qtde de cada item naquela venda
    public List<ItemPayloadDTO> getItens(Long id){
        List<ItemPayloadDTO> listaItens = null;
        getVenda(id).getItens().forEach( itemVenda -> {
            ItemPayloadDTO itemPayloadDTO = new ItemPayloadDTO();
            itemPayloadDTO.setNome(itemVenda.getItem().getNome());
            itemPayloadDTO.setValor(itemVenda.getValor_unitario());
            itemPayloadDTO.setQtde(itemVenda.getQtde());
            listaItens.add(itemPayloadDTO);
        });
        return listaItens;
    }

    //Retorna o vendedor que efetuou a venda
    public PessoaPayloadDTO getVendedor(Long id){
        VendaEntity vendaEntity = getVenda(id);
        PessoaPayloadDTO pessoaPayloadDTO = new PessoaPayloadDTO();
        pessoaPayloadDTO.setNome(funcionarioService.build(funcionarioService.getDTOById(vendaEntity.getFuncionario().getId())).getNome());
        pessoaPayloadDTO.setSobrenome(funcionarioService.build(funcionarioService.getDTOById(vendaEntity.getFuncionario().getId())).getSobrenome());
        return pessoaPayloadDTO;
    }

    //Retorna o valor total da Venda
    public Double getValorTotal(Long id){
        VendaEntity vendaEntity = getVenda(id);
        AtomicReference<Double> valorTotal= new AtomicReference<>(0.0);
        vendaEntity.getItens().forEach(itemVenda -> valorTotal.updateAndGet(v1 -> v1 + itemVenda.getValor_unitario() * itemVenda.getQtde()));
        return valorTotal.get();
    }

    //Retorna os dados do cliente daquela venda
    public PessoaPayloadDTO getCliente(Long id){
        VendaEntity vendaEntity = getVenda(id);
        PessoaPayloadDTO pessoa = new PessoaPayloadDTO();
        pessoa.setNome(vendaEntity.getPessoa().getNome());
        pessoa.setSobrenome(vendaEntity.getPessoa().getSobrenome());
        pessoa.setCpf(vendaEntity.getPessoa().getCpf());
        pessoa.setTelefone(vendaEntity.getPessoa().getTelefone());
        return pessoa;
    }

    //POST
    public void save(VendaPayloadDTO vendaDTO){
        VendaEntity newVenda = new VendaEntity();
        newVenda.setData(vendaDTO.getData());
        newVenda.setPessoa(pessoaService.build(pessoaService.getDTOById(vendaDTO.getId_cliente())));
        newVenda.setFuncionario(funcionarioService.build(funcionarioService.getDTOById(vendaDTO.getId_vendedor())));
        vendaRepository.save(newVenda);
    }

    public void createVenda(VendaPayloadDTO vendaDTO){
        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setData(vendaDTO.getData());
        vendaEntity.setPessoa(pessoaService.build(pessoaService.getDTOById(vendaDTO.getId_cliente())));
        vendaEntity.setFuncionario(funcionarioService.build(funcionarioService.getDTOById(vendaDTO.getId_vendedor())));
    }

    //Adiciona um itemVenda na lista de itens daquela venda
    public void addItemVenda(ItemVendaDTO itemVendaDTO){ //Adiciona um item na lista de itens daquela venda
        itemVendaService.save(itemVendaDTO);
    }

    //PUT
    public void update(VendaDTO vendaDTO){
        VendaEntity vendaEntity = getVenda(vendaDTO.getId());
        if(vendaDTO.getData()!=null){
            vendaEntity.setData(vendaDTO.getData());
        }
        if(vendaDTO.getId_cliente()!=null){
            vendaEntity.setPessoa(pessoaService.getPessoaById(vendaDTO.getId_cliente()));
        }

        if(vendaDTO.getId_vendedor()!=null){
            vendaEntity.setFuncionario(funcionarioService.getFuncionarioById(vendaDTO.getId_vendedor()));
        }
    }


    //DELETE
    public void delete(Long id){
        VendaEntity venda = getVenda(id);
        vendaRepository.delete(venda);
    }
    public void deleteItemVenda(Long id_item){
        itemVendaService.delete(id_item);
    }
}