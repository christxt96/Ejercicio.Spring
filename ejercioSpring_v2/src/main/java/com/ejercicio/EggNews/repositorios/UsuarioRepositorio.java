package com.ejercicio.EggNews.repositorios;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ejercicio.EggNews.entidades.Usuario;
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{

@Query("SELECT u FROM Usuario u WHERE u.email =:email")
 public Usuario BuscarPorEmail(@Param("email") String email);
 
 @Query("SELECT n FROM Usuario n ORDER BY n.nombre DESC")
    public List<Usuario>listDeUsuarios();
}
