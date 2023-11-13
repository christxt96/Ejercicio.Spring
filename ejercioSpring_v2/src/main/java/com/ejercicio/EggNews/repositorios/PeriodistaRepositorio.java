package com.ejercicio.EggNews.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejercicio.EggNews.entidades.Periodista;
@Repository
public interface PeriodistaRepositorio extends JpaRepository<Periodista, String>{

    
}
