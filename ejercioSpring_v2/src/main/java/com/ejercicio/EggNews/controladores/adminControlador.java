package com.ejercicio.EggNews.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ejercicio.EggNews.entidades.Administrador;
import com.ejercicio.EggNews.entidades.Periodista;
import com.ejercicio.EggNews.entidades.Usuario;
import com.ejercicio.EggNews.servicios.NoticiaService;
import com.ejercicio.EggNews.servicios.UsuarioService;
import com.ejercicio.excepciones.Miexcepcion;

@Controller
@RequestMapping("/Admin")
public class adminControlador {
    @Autowired
private NoticiaService ns;
@Autowired
private UsuarioService usuarioService;


    @GetMapping("/Dashboard")
    public String index(ModelMap modelMap ,HttpSession session) {
Usuario user = (Usuario) session.getAttribute("usuario");
if ( user instanceof Periodista){
Periodista period = (Periodista) session.getAttribute("usuario");
modelMap.put("MisNoticias", usuarioService.listaDeNoticias(period.getId()));
}
if ( user instanceof Administrador){
Administrador period = (Administrador) session.getAttribute("usuario");
modelMap.put("MisNoticias", usuarioService.listaDeNoticias(period.getId()));
}


  modelMap.addAttribute("noticias", ns.listDeNoticias());
modelMap.put("usuarios",usuarioService.listDeUsuarios());
modelMap.put("periodistas",usuarioService.listadePeriodistas());

        return "PanelAdmin.html";
    }
     @GetMapping("/EliminarNoticia/{id}")
    public String EliminarNoticia(@PathVariable String id) {
        ns.borrarNoticia(id);
        return "eliminarNoticia.html";
    }
    @GetMapping("/EditarNoticia/{id}")
    public String EditarNoticia(@PathVariable String id, ModelMap model,HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        model.put("noticia", ns.getOne(id));
        return "editarNoticia.html";
    }


      @PostMapping("/EditarNoticia/{id}")	
    public String Guardar( @PathVariable String id ,  String titulo, HttpSession session ,String cuerpo, MultipartFile imagen,ModelMap modelMap ) {
        try {
            Usuario user = (Usuario) session.getAttribute("usuario");
            ns.actualizarNoticia(id, cuerpo, titulo ,imagen );
            modelMap.put("exito", "la noticia se creo con exito");
            return "redirect:/Admin/Dashboard";
        } catch (Exception e) {
            modelMap.put("error", "la noticia no se pudo crear correctamente");
    
            return "editarNoticia.html";
        }
    }
    
    @GetMapping("/CrearNoticia")
    public String CrearNoticia(HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        return "crearNoticia.html";
    }

@PostMapping("/modificarRoles/{id}")
public String ModificarRoles(@PathVariable String id ,@RequestParam String rol ){
    
usuarioService.cambiarRoles(id, rol);


return "redirect:/Admin/Dashboard";


}

    @PostMapping("/Guardar")
    public String Guardar(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelMap 
    ,@RequestParam("file") MultipartFile imagen,HttpSession session )  {
        try {
                Periodista user = (Periodista) session.getAttribute("usuario");
                System.out.println("imagen :"+ imagen.getOriginalFilename());
                ns.crearNoticia(cuerpo, titulo, imagen,user.getId());
    
                modelMap.put("exito", "la noticia se creo con exito");
          return "crearNoticia.html";
        } catch (Miexcepcion e) {
            modelMap.put("error",e.getMessage());
            return "crearNoticia.html";
           
        }

    }
    
    @GetMapping("/{id}")
    
    public String Noticia(@PathVariable String id,HttpSession session, ModelMap model) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        model.put("noticia", ns.getOne(id));
        return "noticia.html";
    }




}
