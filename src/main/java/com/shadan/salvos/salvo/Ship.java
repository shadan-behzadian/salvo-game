package com.shadan.salvos.salvo;

import javax.persistence.*;
import java.util.List;

@Entity
//its very important to write entity annotation thebegining of the class becouse it is indecation to springboot that this
//is a table to later you can map over it. the relatioship many to one , one to many would not work if you dont specify the
//class as an entity
public class Ship {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    //to use a list of things as an argument
    //@ElementCollection is used to create lists of embeddable objects.
    // An embeddable object is data intended for use only in the object containing it.
    //All built-in data types, like Integer and String, are embeddable. In the parent Java class, you just create
    // a collection, e.g., a List or Set, and mark it with @ElementCollection and give a column name.
    @ElementCollection
    @Column(name = "shipLocations")
    private List<String> location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="gameplayer_id")
    private GamePlayer gameplayer;


    public Ship(){}

    public Ship(String type, List<String> location, GamePlayer gamePlayer){
        this.type = type;
        this.location = location;
        this.gameplayer =gamePlayer;
    }

    public Long getId(){
        return id;
    }

    public String getType(){
        return type;
    }

  public void setType(String newType){
        this.type =newType;
  }

  public List<String>  getLocation(){
      return   this.location;
  }

  public void setLocation(List<String> newLocation){
        this.location = newLocation;
  }


 public GamePlayer getGamePlayer(){
       return this.gameplayer;
 }


    public void setOwnerGamePlayer(GamePlayer gamePlayer){

        this.gameplayer = gamePlayer;
    }


}
