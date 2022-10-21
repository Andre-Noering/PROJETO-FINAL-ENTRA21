package com.entra21.LojaSimulator.model.dto;

import lombok.Data;

@Data
public class PessoaDTO {
    private Long idPessoa;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Long idLoja;

    public PessoaDTO(Long idPessoa, String nome, String sobrenome, String cpf, String telefone, Long idLoja) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.idLoja = idLoja;
    }
    public PessoaDTO(){};
}
