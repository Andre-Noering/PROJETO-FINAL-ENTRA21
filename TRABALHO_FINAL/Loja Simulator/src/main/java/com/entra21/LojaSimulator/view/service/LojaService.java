package com.entra21.LojaSimulator.view.service;

import com.entra21.LojaSimulator.model.dto.*;
import com.entra21.LojaSimulator.model.entity.*;
import com.entra21.LojaSimulator.view.repository.FornecedorRepository;
import com.entra21.LojaSimulator.view.repository.FuncionarioRepository;
import com.entra21.LojaSimulator.model.entity.LojaEntity;
import com.entra21.LojaSimulator.view.repository.LojaRepository;
import com.entra21.LojaSimulator.view.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private ItemService itemService;


    @Autowired
    PessoaService pessoaService;

    @Autowired
    FornecedorService fornecedorService;

    public LojaEntity getById(Long id) {
        return lojaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loja não encontrada!"));
    }


    //GET
    public List<ItemDTO> getItensById(Long id) {
        LojaEntity loja = getById(id);
        return loja.getItens().stream().map(item -> itemService.getDTOById(item.getId())).collect(Collectors.toList());
    }

    public List<PessoaDTO> getFuncionariosById(Long id) {
        LojaEntity loja = getById(id);
        return loja.getFuncionarios().stream().map(func -> pessoaService.getDTOById(func.getId())).collect(Collectors.toList());
    }

    public List<FornecedorDTO> getFornecedoresById(Long id) {
        LojaEntity loja = getById(id);
        return loja.getFornecedores().stream().map(fornecedor -> fornecedorService.getDTOById(fornecedor.getId())).collect(Collectors.toList());
    }

    public LojaGerenteDTO getGerenteById(Long id) {
        LojaEntity loja = getById(id);
        return new LojaGerenteDTO(loja.getGerente());
    }

    public LojaContatoDTO getContatoById(Long id) {
        LojaEntity loja = getById(id);
        return new LojaContatoDTO(loja.getContato());
    }

    public LojaValorCaixaDTO getValorCaixaById(Long id) {
        LojaEntity loja = getById(id);
        return new LojaValorCaixaDTO(loja.getValorCaixa());
    }

    public LojaEntity getByRazaoSocial(String razaoSocial){
        return lojaRepository.findByRazaoSocial(razaoSocial);
    }

    public LojaDTO getDTOById(Long id) {
        LojaEntity loja = getById(id);
        return new LojaDTO(loja.getId(), loja.getRazaoSocial(), loja.getCnpj(), loja.getContato(), loja.getValorCaixa(), loja.getGerente(), loja.getItens(), loja.getFornecedores(), loja.getFuncionarios());
    }


    //POST
    public void save(@RequestBody LojaDTO input) {
        LojaEntity newLoja = new LojaEntity();
        newLoja.setRazaoSocial(input.getRazaoSocial());
        newLoja.setCnpj(input.getCnpj());
        newLoja.setContato(input.getContato());
        newLoja.setValorCaixa(input.getValorCaixa());
        newLoja.setGerente(((FuncionarioEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        lojaRepository.save(newLoja);
    }

    //PUT
    public void update(@RequestBody LojaUpdateDTO dto) {
        LojaEntity loja = getById(dto.getId());
        if (dto.getGerente() != null) {
            loja.setGerente(dto.getGerente());
        }
        if (dto.getContato() != null) {
            loja.setContato(dto.getContato());
        }
        if (dto.getFuncionarios() != null) {
            loja.setFuncionarios(dto.getFuncionarios());
        }
        if (dto.getValorCaixa() != null) {
            loja.setValorCaixa(dto.getValorCaixa());
        }
        if (dto.getItens() != null) {
            loja.setItens(dto.getItens());
        }
        if (dto.getFornecedores() != null) {
            loja.setFornecedores(dto.getFornecedores());
        }
        lojaRepository.save(loja);
    }


    public void delete(Long id) {
        LojaEntity fornecedor = getById(id);
        lojaRepository.delete(fornecedor);
    }
    public void deleteByRazaoSocial(String razaoSocial){
        lojaRepository.deleteByRazaoSocial(razaoSocial);
    }



}
