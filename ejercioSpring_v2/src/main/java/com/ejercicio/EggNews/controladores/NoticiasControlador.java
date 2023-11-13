package com.ejercicio.EggNews.controladores;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.ejercicio.EggNews.entidades.Noticia;
import com.ejercicio.EggNews.entidades.Usuario;
import com.ejercicio.EggNews.servicios.NoticiaService;


import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/noticia")
public class NoticiasControlador {

    @Autowired
    private NoticiaService ns;


    @GetMapping("/{id}")
    public String Noticia(@PathVariable String id, ModelMap model, HttpSession session) {
Noticia noticia = ns.getOne(id);
Usuario user = (Usuario) session.getAttribute("usuario");
        model.put("noticia",noticia);
        String texto = noticia.getCuerpo();
        String[] parrafos = texto.split("\n");
  model.addAttribute("cuerpos", parrafos);
      
        return "noticia.html";
    }

     
    @PostMapping("/search")
    public String searchNoticias(@RequestParam("search") String searchTerm, ModelMap model,HttpSession session){
        Usuario user = (Usuario) session.getAttribute("usuario");
        model.addAttribute("noticias", ns.buscarPorPalabraClave(searchTerm));
        return "search.html";
    }
     

}
