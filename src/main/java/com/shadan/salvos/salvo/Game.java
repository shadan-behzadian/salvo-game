package com.shadan.salvos.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;
    private Date date;

//what you write in mappedBy = " " ha to equal to the instance you made in the othe class
    //so for example the relationship between gameplayer and game is many to one and the column made in it is from calss
    //Game and its an instance called game so we map over that.
    @OneToMany(mappedBy="game",fetch = FetchType.EAGER)
    Set<GamePlayer> gameplayers = new HashSet<>();

    @OneToMany(mappedBy="game",fetch = FetchType.EAGER)
    Set<Score> scores = new HashSet<>();



    public Game(){}


   public Game(Date date){

        this.date = date;
    }

    public Date getToday() {

        return date;
    }

    public void setToday(Date today) {

        this.date = today;
    }

    public Long getId() {

        return Id;
    }
    public Set<GamePlayer> getGameplayers(){

        return gameplayers;
    }

    //this is the instance that you create in GamePlayer
    public void addGamePlayer(GamePlayer gameplayer){

        gameplayer.setOwnerGame(this);
        gameplayers.add(gameplayer);

    }

    public Set<Score> getScores(){

        return scores;
    }

    public void addScore(Score score){

        score.setOwnerGame(this);
        scores.add(score);

    }
//    public Map<String, Object> toDTO() {
//        Map<String, Object> dto = new LinkedHashMap<String, Object>();
//        dto.put("id", getId());
//        dto.put("date", getToday());
//        return dto;
//    }

}
