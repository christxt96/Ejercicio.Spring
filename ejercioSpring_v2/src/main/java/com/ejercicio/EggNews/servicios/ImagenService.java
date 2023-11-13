package com.ejercicio.EggNews.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ejercicio.EggNews.entidades.imagen;
import com.ejercicio.EggNews.repositorios.ImagenRepositorio;
import com.ejercicio.excepciones.Miexcepcion;

@Service
public class ImagenService {
    /**
     *
     */
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public imagen guardar(MultipartFile archivo) throws Miexcepcion {

        if (archivo != null) {
            try {
                imagen img = new imagen();

                img.setMine(archivo.getContentType());
                img.setNombre(archivo.getName());
                img.setContenido(archivo.getBytes());
                return imagenRepositorio.save(img);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());

            }

        }
        return null;
    }

    public imagen actualizar(MultipartFile archivo, String IdImagen) {

        if (archivo != null) {
            try {
                imagen img = new imagen();

                if (IdImagen != null) {
                    Optional<imagen> respuesta = imagenRepositorio.findById(IdImagen);

                    if (respuesta.isPresent()) {
                        img = respuesta.get();
                    }

                }

                img.setMine(archivo.getContentType());
                img.setNombre(archivo.getName());
                img.setContenido(archivo.getBytes());
                return imagenRepositorio.save(img);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());

            }

        }
        return null;
    }

}