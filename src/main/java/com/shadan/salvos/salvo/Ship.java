package com.shadan.salvos.salvo;

import javax.persistence.*;


public class Ship {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;
    String type;
    String location;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn (name ="GamePlayer_id")
    public GamePlayer gamePlayer;


    public Ship(){}

    Ship(String type, String location){
        this.type = type;
        this.location = location;
    }

    public void setOwnerGamePlayer(GamePlayer gamePlayer){

        this.gamePlayer = gamePlayer;
    }


}
