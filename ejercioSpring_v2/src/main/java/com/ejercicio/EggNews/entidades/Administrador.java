package com.ejercicio.EggNews.entidades;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity

@Data
@EqualsAndHashCode(callSuper = false)
public class Administrador  extends Usuario{

@OneToMany
private List<Periodista> ListDePeriodistas;




    
}
