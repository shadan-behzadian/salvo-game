package com.shadan.salvos.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/* FetchType.EAGER tells JPA to make sure that when a gameplayer is loaded
 from the database, the game  data of that game player should be loaded too */

//the @JoinColumn annotation says which column has the ID of the owner

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="player_id")
    private Player player;

    @OneToMany(mappedBy="gameplayer",fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers = new HashSet<>();


    public GamePlayer(){}

    //in the constructor you add the anstances of other classes becouse you want to add thier infor in this table
    public GamePlayer(Date date, Player player, Game game){

        this.date = date;
        this.player = player;
        this.game = game;
    }

    public Date getToday(){

        return date;
    }

    public void setToday(Date today){
        date =today;

    }
    public Long getId(){

        return id;
    }

    public Player getPlayer() {

        return this.player;
    }

    public void setOwnerPlayer(Player player) {

        this.player = player;
    }

    public Game getGame(){

        return this.game;
    }

    public void setOwnerGame(Game game){

        this.game = game;
    }

    public void addShip(Ship ship){

       ship.setOwnerGamePlayer(this);
        gamePlayers.add(ship);

    }
}







