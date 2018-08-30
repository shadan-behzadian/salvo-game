package com.shadan.salvos.salvo;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long Id;
   private double score;
   private Date finishedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="player_id")
    private Player player;


   Score(){}

   public Score(double score,Date finishedDate,Game game,Player player){
    this.score = score;
    this.finishedDate = finishedDate;
    this.game = game;
    this.player = player;

   }

    public Long getId() {
        return Id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(){

    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public Player getPlayer(){
      return this.player;
    }

    public void setOwnerPlayer(Player player){

        this.player = player;
    }


    public Game getGame(){
       return this.game;
    }

    public void setOwnerGame(Game game){

        this.game = game;
    }






}
