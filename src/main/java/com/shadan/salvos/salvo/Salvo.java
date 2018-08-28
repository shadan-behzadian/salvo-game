package com.shadan.salvos.salvo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer turn;

    @ElementCollection
    @Column(name = "shipLocations")
    private List<String> location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gameplayer_id")
    private GamePlayer gamePlayer;


    public Salvo(){}

    public Salvo(Integer turn, List<String> location, GamePlayer gamePlayer){
        this.turn = turn;
        this.location = location;
        this.gamePlayer = gamePlayer;
    }

    public Long getId(){
        return this.id;
    }

    public Integer getTurn(){
        return this.turn;
    }

    public void setTurn(Integer newturn){
        this.turn = newturn;
    }

    public List<String> getLocation(){
        return this.location;
    }

    public void setLocation(List<String> newLocation){
        this.location = newLocation;
    }

    public GamePlayer getGamePlayer(){
        return this.gamePlayer;
    }

    public void setOwnerGamePlayerForSalvo(GamePlayer gamePlayer){
       this.gamePlayer = gamePlayer;
    }

}
