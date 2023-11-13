package com.ejercicio.EggNews.entidades;


import org.hibernate.annotations.GenericGenerator;

import com.ejercicio.EggNews.enumeracion.Rol;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
  
    private String nombre;
  
    private String email;
 
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
   
    private boolean activo;

    
    @Temporal(TemporalType.DATE)
    private Date fechaDeAlta;
  
    @OneToOne
  private imagen imagen ;
}
