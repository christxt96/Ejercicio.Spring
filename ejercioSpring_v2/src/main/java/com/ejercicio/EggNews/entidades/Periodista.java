package com.ejercicio.EggNews.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Periodista extends Usuario {
    @OneToMany
    private List<Noticia> misNoticias = new ArrayList();

    private Integer sueldo;

}
