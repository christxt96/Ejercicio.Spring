/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejercicio.EggNews.entidades;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@ManyToOne
private Periodista periodista;

@Temporal(TemporalType.DATE)
private Date fecha;

  public Noticia() {
  }

  

}
