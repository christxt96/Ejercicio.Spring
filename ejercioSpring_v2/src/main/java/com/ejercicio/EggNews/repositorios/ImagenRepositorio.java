package com.ejercicio.EggNews.repositorios;



import org.springframework.stereotype.Repository;

import com.ejercicio.EggNews.entidades.imagen;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImagenRepositorio extends JpaRepository<imagen, String>{
    
}
