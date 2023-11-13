package com.ejercicio.EggNews.controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ejercicio.EggNews.entidades.Usuario;
import com.ejercicio.EggNews.servicios.UsuarioService;

@Controller
@RequestMapping("/Imagen")
public class imagenControlador {
    
@Autowired
UsuarioService us;

@GetMapping("/perfil/{id}")
public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id){
Usuario use = us.getOne(id);
byte[] imagen = use.getImagen().getContenido();
HttpHeaders headers = new HttpHeaders();

headers.setContentType(MediaType.IMAGE_JPEG);


return new ResponseEntity(imagen,headers,HttpStatus.OK);
}
}
