package com.shadan.salvos.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;


//We want to save instances of Person in a persistent database, i.e.,
// one that doesn't disappear when our program stops running.
//Thanks to JPA and Spring Boot, this can be done by adding very little to the class definition.
//If there are fields that should not be saved, e.g., because they hold temporary
// scratch data, annotate them with @Transient.
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/*The annotation @Entity tells Spring to create a player table for this class.
        The annotation @Id says that the id instance variable holds the database key for this class.
        The annotation @GeneratedValue tells JPA to get the Id from the DBMS.*/

@Entity
//tells Spring to create a player table for this class.
public class Player {
    @Id
    //says that the id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO)
    //tells JPA to get the Id from the DBMS.
    //So, when you add the Id annotation to the id field, JPA will also persist
    // all other fields. since these are naturally the other columns in the database.
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    @OneToMany(mappedBy="player",fetch = FetchType.EAGER)
    Set<GamePlayer> gameplayers = new HashSet<>();

    @OneToMany(mappedBy="player",fetch = FetchType.EAGER)
    Set<Score> scores = new HashSet<>();

    public Player(){}

    public Player(String first, String last , String user,String password){
        firstName = first;
        lastName = last;
        userName = user;
        this.password = password;

    }

    @JsonIgnore
    public Set<GamePlayer> getGameplayers(){
        return gameplayers;
    }

    //The addGamePlayer() method lets us connect a player to a gameplayer. If we call the line of code
    public void addGamePlayer(GamePlayer gameplayer){

        /*Java sets the "this" variable to the instance of Player in player.
        Whenever a method is called on an instance, Java sets this to the instance.*/
            gameplayer.setOwnerPlayer(this);
            gameplayers.add(gameplayer);
//The code adds the gameplayer to the set of gameplayers for this player.
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }
    public Long getId(){

        return id;
    }

    public String toString(){
        return firstName + " " + lastName + " " + userName;
    }

@JsonIgnore
    public Set<Score> getScores(){

        return scores;
    }

    public void addScore(Score score){

        score.setOwnerPlayer(this);
       scores.add(score);

    }



 //to get the score of the player based on the game
//    public Score getScoreOfGame(Game game){
//      Score score = game.getScores()
//              .stream()
//              //this.id here means the id of this player instance
//              .filter(score1 -> score1.getPlayer().getId() == this.id)
//              .findFirst()
//              .orElse(null);
//
//
//       return score;
//    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}

