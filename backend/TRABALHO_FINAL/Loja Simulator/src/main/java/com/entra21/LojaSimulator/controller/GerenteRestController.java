package com.entra21.LojaSimulator.controller;

import com.entra21.LojaSimulator.model.dto.FuncionarioDTO;
import com.entra21.LojaSimulator.model.dto.LojaDTO;
import com.entra21.LojaSimulator.view.service.FuncionarioService;
import com.entra21.LojaSimulator.view.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class GerenteRestController {
    @Autowired
    private LojaService lojaService;
    @PostMapping("/adicionar")
    public void save(LojaDTO lojaDTO){
        lojaService.save(lojaDTO);
    }

    @PostMapping(name="/login")
    public FuncionarioDTO login(String username, String password){
        return funcionarioService.login(username,password);
    }
}