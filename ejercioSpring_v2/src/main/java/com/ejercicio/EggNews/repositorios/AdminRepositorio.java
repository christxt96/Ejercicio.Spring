package com.ejercicio.EggNews.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejercicio.EggNews.entidades.Administrador;
import com.ejercicio.EggNews.entidades.Usuario;
@Repository
public interface AdminRepositorio extends JpaRepository<Administrador , String> {

    
}