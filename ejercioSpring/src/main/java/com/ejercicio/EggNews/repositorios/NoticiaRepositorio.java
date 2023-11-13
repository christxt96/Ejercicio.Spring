/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ejercicio.EggNews.repositorios;

import com.ejercicio.EggNews.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author crist
 */
@Repository
public interface NoticiaRepositorio  extends JpaRepository<Noticia, String>{
    @Query("SELECT n From Noticia n WHERE n.titulo = :titulo ")
    public List<Noticia> BuscarNoticia(@Param("titulo") String titulo);
    
    @Query("SELECT n FROM Noticia n ORDER BY n.fecha DESC")
    public List<Noticia>listDeNoticias();

    @Query("SELECT n FROM Noticia n WHERE n.titulo LIKE %:search% OR n.cuerpo LIKE %:search%")
    List<Noticia> buscarPorPalabraClave(@Param("search") String search);
}
