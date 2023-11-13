package com.ejercicio.EggNews.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ejercicio.EggNews.entidades.Usuario;
import com.ejercicio.EggNews.servicios.NoticiaService;
import com.ejercicio.EggNews.servicios.UsuarioService;
import com.ejercicio.excepciones.Miexcepcion;

@Controller
@RequestMapping("/")
public class noticiacontrolador {
    @Autowired
private NoticiaService noticiaService;
    @Autowired
private UsuarioService usuarioService;
    @GetMapping("/")
    public String index(ModelMap modelMap) {
  modelMap.addAttribute("noticias", noticiaService.listDeNoticias());

        return "index.html";
    }
 
@GetMapping("/register")
public String register(ModelMap modelMap,HttpSession session) {
    Usuario user = (Usuario) session.getAttribute("usuario");
    return "register.html";
}

@ PostMapping ("/registro" )
 public String registro(@RequestParam() MultipartFile image ,@RequestParam() String nombre,@RequestParam() String email,@RequestParam() String password ,  @RequestParam() String password2, ModelMap modelMap) {
   
    try {
usuarioService.register(nombre, email, password, password2,image);
modelMap.put("exito", "El usuario se registro con exito");

return "redirect:/";
    } catch (Miexcepcion ex) {
      modelMap.put("error", ex.getMessage());
      
  modelMap.put("nombre", nombre);
    modelMap.put("email", email);
return "register.html";


    }

}




@GetMapping("/login")
public String login(ModelMap modelMap, @RequestParam(required = false) String error,HttpSession session) {
    Usuario user = (Usuario) session.getAttribute("usuario");
if (error!=null ) {
    modelMap.put("error", "Usuario o contraseña invalida ");




}

    return "login.html";
}


@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PERIODISTA')")
@GetMapping("/inicio")
 public String inicio(ModelMap modelMap, HttpSession session) throws Exception {
      modelMap.addAttribute("noticias", noticiaService.listDeNoticias());
      Usuario logeado = (Usuario) session.getAttribute("usuario");

      if (logeado.getRol().toString().equals("ADMIN")) {
        return "redirect:/Admin/Dashboard";
      }
    return "inicio.html";
}

@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PERIODISTA')")
@GetMapping("/perfil")
 public String paneladmin(ModelMap modelMap, HttpSession session){

    Usuario user = (Usuario) session.getAttribute("usuario");
modelMap.put("usuario", user);
return "ModificarUsuario.html";

}

@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PERIODISTA')")
@PostMapping("/perfil/{id}")
public String actualizar ( @RequestParam() MultipartFile image, HttpSession session, @PathVariable String id ,@RequestParam() String nombre,@RequestParam() String email,@RequestParam() String password ,  @RequestParam() String password2, ModelMap modelMap) {
   
try {
 usuarioService.actualizar(nombre, id, email, password, password2, image);
modelMap.put("exito", "Usuario actualizado correctamente");
Usuario user = (Usuario) session.getAttribute("usuario");

return "inicio.html";
} catch (Miexcepcion ex) {
    modelMap.put("error", ex.getMessage() );
    modelMap.put("nombre",nombre);
    modelMap.put("email", email);
    return"ModificarUsuario.html";
    

}
}

}
