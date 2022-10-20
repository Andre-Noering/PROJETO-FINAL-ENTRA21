package com.entra21.LojaSimulator.controller;

import com.entra21.LojaSimulator.model.dto.*;
import com.entra21.LojaSimulator.view.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{razao_social}")
public class LojaRestController {
    @Autowired
    LojaService lojaService;
    @GetMapping
    public LojaPayloadDTO getLoja(@PathVariable String razao_social){
        return lojaService.getDTOById(lojaService.getByRazaoSocial(razao_social).getId());
    }


    @PutMapping
    public void update(@RequestBody LojaUpdateDTO lojaDTO){
        lojaService.update(lojaDTO);
    }

    @DeleteMapping
    public void delete(@PathVariable String razaoSocial){
        lojaService.deleteByRazaoSocial(razaoSocial);
    }
}
