package com.ejercicio.EggNews.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ejercicio.EggNews.entidades.Noticia;
import com.ejercicio.EggNews.repositorios.NoticiaRepositorio;
import com.ejercicio.excepciones.Miexcepcion;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepositorio nr;

    public NoticiaService() {
    }

    @Transactional
    public void crearNoticia(String cuerpo, String titulo, MultipartFile imagen) throws Miexcepcion {
      validar(cuerpo, titulo, imagen);
     

            try {
                Path directotyImage = Paths.get("src//main/resources/static/image");
                String ruta = directotyImage.toFile().getAbsolutePath();
                byte[] rutaBytes;
                rutaBytes = imagen.getBytes();
                Path rutaCompleta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, rutaBytes);
                Noticia noticia = new Noticia();
                noticia.setImage(imagen.getOriginalFilename());
                noticia.setTitulo(titulo);
                noticia.setCuerpo(cuerpo);
                noticia.setFecha(new Date());
                nr.save(noticia);

            } catch (Exception e) {

            throw new Miexcepcion("error al cargar imagen, verique la imagen e intentelo nueva mente");

            }

        }

        
    

    @Transactional
    public void borrarNoticia(String id) {
        Noticia noticia = nr.findById(id).get();
        nr.delete(noticia);
    }

    @Transactional
    public void actualizarNoticia(String id, String cuerpo, String titulo ,MultipartFile imagen) throws Miexcepcion {
         validar(cuerpo, titulo, imagen);
       
        Optional<Noticia> noticia = nr.findById(id);
            Noticia n = noticia.get();
            n.setTitulo(titulo);
            n.setCuerpo(cuerpo);
            n.setFecha(new Date());
            nr.save(n);
   
    }

    public List<Noticia> listDeNoticias() {

        List<Noticia> noticias = nr.listDeNoticias();

        return noticias;

    }

    public Noticia getOne(String id) {
        return nr.getOne(id);
    }

    public List<Noticia> buscarPorPalabraClave(String palabra) {
        List<Noticia> noticias = nr.buscarPorPalabraClave(palabra);
        return noticias;

    }

    public List<Noticia> buscarPorTitulo(String titulo) {
        List<Noticia> noticias = nr.BuscarNoticia(titulo);
        return noticias;
    }
private void  validar(String cuerpo, String titulo, MultipartFile imagen) throws Miexcepcion{

if(cuerpo.isEmpty() || cuerpo== null){
throw new Miexcepcion("el cuerpo no puede ser nulo o estar vacio");
}

if(titulo.isEmpty() || titulo== null){
throw new Miexcepcion("el titulo no puede ser nulo o estar vacio");
}
   if (!imagen.isEmpty() || imagen == null ) {
   throw new Miexcepcion("tiene que agregar una imagen para pode guardar la noticia");

}



}
}
