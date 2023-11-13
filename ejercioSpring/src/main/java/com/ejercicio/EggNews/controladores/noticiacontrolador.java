package com.ejercicio.EggNews.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ejercicio.EggNews.servicios.NoticiaService;

@Controller
@RequestMapping("/")
public class noticiacontrolador {
    @Autowired
private NoticiaService noticiaService;

    @GetMapping("/")
    public String index(ModelMap modelMap) {
  modelMap.addAttribute("noticias", noticiaService.listDeNoticias());

        return "index.html";
    }
 

}
