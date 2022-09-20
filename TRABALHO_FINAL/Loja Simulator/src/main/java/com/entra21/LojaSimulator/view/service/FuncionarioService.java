package com.entra21.LojaSimulator.view.service;

import com.entra21.LojaSimulator.model.dto.FornecedorDTO;
import com.entra21.LojaSimulator.model.dto.FuncionarioDTO;
import com.entra21.LojaSimulator.model.dto.FuncionarioVendaDTO;
import com.entra21.LojaSimulator.model.entity.*;
import com.entra21.LojaSimulator.view.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class FuncionarioService implements UserDetailsService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioEntity getFuncionarioById(Long id){
        return funcionarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Funcionario não encontrada!"));
    }

    //Método Get - Retornando o funcionário pelo ID
    public FuncionarioDTO findFuncById(Long id){
        FuncionarioEntity funcionario = getFuncionarioById(id);
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(funcionario.getId());
        funcionarioDTO.setNome(funcionario.getNome());
        funcionarioDTO.setSobrenome(funcionario.getSobrenome());
        funcionarioDTO.setCpf(funcionario.getCpf());
        funcionarioDTO.setTelefone(funcionario.getTelefone());
        return funcionarioDTO;
    }

    //Build do funcionário
    public FuncionarioEntity build(FuncionarioDTO funcionarioDTO){
        FuncionarioEntity newFuncionario = new FuncionarioEntity();
        newFuncionario.setId(funcionarioDTO.getId());
        newFuncionario.setNome(funcionarioDTO.getNome());
        newFuncionario.setSobrenome(funcionarioDTO.getSobrenome());
        newFuncionario.setTelefone(funcionarioDTO.getTelefone());
        newFuncionario.setCpf(funcionarioDTO.getCpf());
        return newFuncionario;
    }

    //Método Get - Retornando todas as vendas do funcionário filtrando o funcionário pelo iD
    public FuncionarioVendaDTO getVendasFuncionario(Long id) {
        Optional<FuncionarioEntity> funcionario = funcionarioRepository.findById(id);
        return funcionario.map(funcionarioEntity -> new FuncionarioVendaDTO(funcionarioEntity.getVendas())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado!"));
    }

    //Metodo Save - Criando uma venda em um funcionário
    public void saveVenda(Long id){
        FuncionarioEntity funcionario = getFuncionarioById(id);
        VendaEntity venda = new VendaEntity();//chamar metodo
        venda.setFuncionario(funcionario);
        funcionario.getVendas().add(venda);
    }

    public FuncionarioDTO getDTOById(Long id) {
        FuncionarioEntity funcionario = getFuncionarioById(id);
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setLogin(funcionario.getLogin());
        dto.setSenha(funcionario.getSenha());
        dto.setCpf(dto.getCpf());
        dto.setSobrenome(dto.getSobrenome());
        dto.setTelefone(dto.getTelefone());
        return dto;
    }

    //Metodo Save - Criando uma venda em um funcionário
    public void savePedidoCompra(Long id){
        FuncionarioEntity funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado!"));
        PedidoCompraEntity venda = new PedidoCompraEntity();//chamar metodo
        venda.setFuncionario(funcionario);
        funcionario.getPedidos().add(venda);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FuncionarioEntity en = funcionarioRepository.findByLogin(username);
        if (en == null) {
            throw new UsernameNotFoundException(username);
        }
        return en;
    }

}
