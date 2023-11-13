package com.ejercicio.EggNews.controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ejercicio.EggNews.entidades.Noticia;
import com.ejercicio.EggNews.servicios.NoticiaService;
import com.ejercicio.excepciones.Miexcepcion;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/noticia")
public class NoticiasControlador {

    @Autowired
    private NoticiaService ns;

    @GetMapping("/CrearNoticia")
    public String CrearNoticia() {

        return "crearNoticia.html";
    }

    @GetMapping("/{id}")
    public String Noticia(@PathVariable String id, ModelMap model) {
Noticia noticia = ns.getOne(id);
        model.put("noticia",noticia);
        String texto = noticia.getCuerpo();
        System.out.println(texto);
        String[] parrafos = texto.split("\n");
  model.addAttribute("cuerpos", parrafos);
      
        return "noticia.html";
    }

    @PostMapping("/Guardar")
    public String Guardar(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelMap 
    ,@RequestParam("file") MultipartFile imagen  )  {
        try {
                ns.crearNoticia(cuerpo, titulo, imagen);
            modelMap.addAttribute("noticias", ns.listDeNoticias());
        
                modelMap.put("exito", "la noticia se creo con exito");
          return "crearNoticia.html";
        } catch (Miexcepcion e) {
            modelMap.put("error",e.getMessage());
            return "crearNoticia.html";
           
        }

    }
     
    @PostMapping("/search")
    public String searchNoticias(@RequestParam("search") String searchTerm, ModelMap model){

        model.addAttribute("noticias", ns.buscarPorPalabraClave(searchTerm));
        return "search.html";
    }
     

}
