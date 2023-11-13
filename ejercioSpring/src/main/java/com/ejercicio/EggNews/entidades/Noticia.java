/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejercicio.EggNews.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author crist
 */
@Entity
@Data
public class Noticia {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private String titulo;
@Column(columnDefinition = "LONGTEXT")
  private String cuerpo;
@Column(columnDefinition = "LONGBLOB")
private String image;

@Temporal(TemporalType.DATE)
private Date fecha;

  public Noticia() {
  }

  

}
