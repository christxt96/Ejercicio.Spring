package com.ejercicio.EggNews.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ejercicio.EggNews.servicios.NoticiaService;

@Controller
@RequestMapping("/")
public class adminControlador {
    @Autowired
private NoticiaService ns;

    @GetMapping("/paneladmin")
    public String index(ModelMap modelMap) {
  modelMap.addAttribute("noticias", ns.listDeNoticias());

        return "PanelAdmin.html";
    }
     @GetMapping("/EliminarNoticia/{id}")
    public String EliminarNoticia(@PathVariable String id) {
        ns.borrarNoticia(id);
        return "eliminarNoticia.html";
    }
    @GetMapping("/EditarNoticia/{id}")
    public String EditarNoticia(@PathVariable String id, ModelMap model) {
        model.put("noticia", ns.getOne(id));
        return "editarNoticia.html";
    }


      @PostMapping("/EditarNoticia/{id}")	
    public String Guardar( @PathVariable String id ,  String titulo,  String cuerpo, ModelMap modelMap) {
        try {
            ns.actualizarNoticia(id, cuerpo, titulo);
            modelMap.put("exito", "la noticia se creo con exito");
            return "redirect:/paneladmin";
        } catch (Exception e) {
            modelMap.put("error", "la noticia no se pudo crear correctamente");
    
            return "editarNoticia.html";
        }
    }

    
    @GetMapping("/{id}")
    public String Noticia(@PathVariable String id, ModelMap model) {
        model.put("noticia", ns.getOne(id));
        return "noticia.html";
    }




}
