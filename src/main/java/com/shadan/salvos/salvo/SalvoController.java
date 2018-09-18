package com.shadan.salvos.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
//controller reads urls sent to the sever to find the approprate java code to create json
//you are creating a pathway for the controller to read from by adding requestmapping(/api) this means it will look for
//urls starting with api then it will look for another request mapping name e.g. if it is /games will look for /api/games
//url and the excecutes all the methodes you assigen to it so that java gets the required code and javascripts can use it later
@RestController
@RequestMapping("/api")
public class SalvoController {


    @Autowired
    private GameRepository gameRepository;

    //you added this when you were adding player info in games json in java 2
    //so when someone tries to log in you want to show their dat ain the games json with all the games
    //thats why you add the player repository an dthats whay you give authentication as parameter
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ShipRepository shipRepository;


    //this method is a get methode you added to see the players when you sign up people
    //becouse you only had a post method for api players and you did npt have an api for post players
//you also added ignore json code in the class of players to ignore the loop in the json

    @RequestMapping("/players")
    public List<Player> Players() {

        return playerRepository.findAll();

    }




//   @Autowired
//    PasswordEncoder passwordEncoder;
    //to post from front end to back end (signup form)
    @RequestMapping(path ="/players", method = RequestMethod.POST)
    //to pass the whole object (@Requestbody) to pass one by on eparameters (@requestparameter)
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Player player) {
        //The @RequestBody annotation tells Spring to take the data in the body of
        // the request, parse it as a JSON
        // object, then map the JSON object to an instance of the class Player.
        if (player.getUserName().isEmpty()) {
            return new ResponseEntity<>(makeMap("error","No UserName given"), HttpStatus.FORBIDDEN);
        }
        Player newPlayer = playerRepository.findByUserName(player.getUserName());
        if (newPlayer != null) {
            return new ResponseEntity<>(makeMap("error","Name already used"), HttpStatus.CONFLICT);
        }
        Player newPlayerAdded = playerRepository.save(new Player(player.getFirstName(),player.getLastName(),player.getUserName(),player.getPassword()));

        return new ResponseEntity<>(makeMap("Player was added with id number",newPlayerAdded.getId()) , HttpStatus.CREATED);
    }
//to make function in the function above
    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }


    @RequestMapping(path = "/games/players/{gamePlayerId}/ships")
    public ResponseEntity<Map<String,Object>> placeShips(Authentication authentication,@PathVariable Long gamePlayerId,@RequestBody List<Ship> ships){

    GamePlayer gamePlayerWhoPlays = gamePlayerRepository.findOne(gamePlayerId);

      if(authentication == null) {
          return new ResponseEntity<>(makeMap("error", "please logIn or SignUp"), HttpStatus.UNAUTHORIZED);
      }
          if(gamePlayerId == null){
              return new ResponseEntity<>(makeMap("error","this player does not exist"),HttpStatus.UNAUTHORIZED);

      }
      //the second time
      if(gamePlayerWhoPlays.getShip().size() > 0){
          return new ResponseEntity<>(makeMap("error","you can not place more ships"),HttpStatus.FORBIDDEN);
      }
      //the first time enters here becouse the gamelayer still does not have any ship assigned to it
        if(ships.size() != 5){
            return new ResponseEntity<>(makeMap("error","you need to place 5 ships"),HttpStatus.UNAUTHORIZED);
        }
        else{
            for (Ship eachShip : ships ) {
              gamePlayerWhoPlays.addShip(eachShip);
              shipRepository.save(eachShip);
            }


        }

        return new ResponseEntity<>(makeMap("Done","ships are placed"),HttpStatus.CREATED);

    }











    //second step in plan of attack to add more details in the object
    //we ar getting the info of the game first getting ID and date then through game we access de data of gameplayers
    //(remmember it was a onetomany relationship so you need to loop aka stream over it) then through the gameplayer we want
    //to access the player info (remember it was a manytoone relationship, so each gameplayer can only have one player so
    //you dont need to stream anymore to get the data of the player.
    @RequestMapping("/games")
    public Map<String, Object> toDTO(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        List<Game> games = gameRepository.findAll();
        dto.put("Games", games
                .stream() //means loop over it, take them one by one.
                .map(game-> gameDto(game)) //and fot each of them apply this methode, and the methode is defined out of this
                .collect(toList()));
        //this means if a user is authenticated then find the player with that username in database
       if(authentication!= null) {
           //If there is a user logged in, then authentication.getName() will return the
           // name that was put into the
           // UserDetails object by the WebSecurityConfiguration class.
           Player player = playerRepository.findByUserName(authentication.getName());
           dto.put("player", playerDto(player));
       }
       else{
           dto.put("player", "guest");
       }

        return dto;
    }

    //to create a new game

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createNewGame(Authentication authentication) {
            Player player = playerRepository.findByUserName(authentication.getName());

            if (authentication == null) {
                return new ResponseEntity<>(makeMap("Error", "You need to log in or sign Up"), HttpStatus.UNAUTHORIZED);
            } else {
                Game newGameAdded = gameRepository.save(new Game(new Date()));
                GamePlayer newGamPlayerAdded = gamePlayerRepository.save(new GamePlayer(new Date(), player, newGameAdded));
                return new ResponseEntity<>(makeMap("gpId", newGamPlayerAdded.getId()), HttpStatus.CREATED);
            }

        }

//to join a game
    @RequestMapping(value = "/game/{gameId}/players",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> joinGame(@PathVariable Long gameId, Authentication authentication){
        Player player = playerRepository.findByUserName(authentication.getName());
Game game = gameRepository.findOne(gameId);
        if(authentication == null){
            return new ResponseEntity<>(makeMap("Error","you need to log in or sign Up"),HttpStatus.UNAUTHORIZED);
        }
        if(game == null){
            return new ResponseEntity<>(makeMap("no Such game is found","Error"),HttpStatus.FORBIDDEN );
        }

       else{
            GamePlayer newGamePlayerCreated = gamePlayerRepository.save(new GamePlayer(new Date(), player, game));
        return new ResponseEntity<>(makeMap("newGamePlayerCreatedID", newGamePlayerCreated.getId()),HttpStatus.CREATED);
       }


    }







//    public  Map<String,Object> currentPlayerDto(Player playerExample) {
//        Map<String, Object> dto = new LinkedHashMap<>();
//            dto.put("id",get.Gameplayer().get)
//    }


    public  Map<String,Object> gameDto(Game game){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",game.getId());
        dto.put("date",game.getToday());

        dto.put("gamePlayers", game.getGameplayers()
                .stream()
                .map(gamePlayer -> gamePlayerDto(gamePlayer))
                .collect(toList()));
        //to create the json for scores
       dto.put("scores", game.getScores()
               .stream()
               .map(score -> scoreOfeach(score))
               .collect(Collectors.toList())

       );

        return dto;
    }

//to create the json for scores
    Map<String,Object> scoreOfeach(Score score) {

        Map<String, Object> dto = new LinkedHashMap<>();
        // System.out.println(gamePlayer.getPlayer().getScoreOfGame(gamePlayer.getGame()).getScore());

            dto.put("playerID", score.getPlayer().getId());
            dto.put("firstname", score.getPlayer().getFirstName());
            dto.put("score",score.getScore());




        return dto;

    }

    public  Map<String,Object> gamePlayerDto(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",gamePlayer.getId());
        dto.put("player", playerDto(gamePlayer.getPlayer()));

        return dto;
    }



//    public  Map<String,Object> playerDto(Player player){
//        Map<String, Object> dto = new LinkedHashMap<>();
//        dto.put("id", player.getId());
//        dto.put("email", player.getUserName());
//
//        return dto;
//    }

//first step of plan of attack to get only ID
//    public List<Long> getListOfGamesId(){
//
//        List<Long> games = new ArrayList<>();
//        games = gameRepository
//                .findAll()
//                .stream()
//                .map(b -> b.getId())
//                .collect(toList());
//
//        return games;
//    }


    @Autowired
    private GamePlayerRepository gamePlayerRepository;
//you added authentication here to only show the game view to the authenticated user
    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> toGameViewDTO(@PathVariable Long gamePlayerId, Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        //toget game player to acces game info and ship info and ...
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerId);
        if(authentication == null){
            return new ResponseEntity<>(makeMap("Error", "You need to log in"),HttpStatus.UNAUTHORIZED);
        }else {
            Player player = playerRepository.findByUserName(authentication.getName());

            if ( player.getId() ==gamePlayer.getPlayer().getId()){
                return new ResponseEntity<>(currentPlayer(gamePlayer), HttpStatus.OK);
            }
        else{
                return new ResponseEntity<>(makeMap("Do not cheat!!!", "play your own game"), HttpStatus.UNAUTHORIZED);
            }

        }

    }

    public Map<String, Object> currentPlayer(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("Id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getToday());
        dto.put("gamePlayers", gamePlayer.getGame().getGameplayers()
                .stream()
                .sorted((gameplayer1, gameplayer2)->gameplayer1.getId().compareTo(gameplayer2.getId()))
                .map(gamePlayerExample -> gameplayerDto(gamePlayerExample))
                .collect(toList()));



        dto.put("ships",gamePlayer.getShip()
                .stream()
                .map(ship -> shipDetail(ship))
                .collect(toList()));

        //you need to sort the id of gameplayers so later on they dont changed all the time in the json (the order)
        dto.put("salvoes",gamePlayer.getGame().getGameplayers()
                .stream()
                .sorted((gameplayer1, gameplayer2)->gameplayer1.getId().compareTo(gameplayer2.getId()))
                .map(gameplayer -> getGameplayer(gameplayer))
                .collect(toList())
        );

        return dto;
    }




    public Map<String, Object> getGameplayer(GamePlayer gameplayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put(gameplayer.getId().toString(), gameplayer.getSalvos()
                        .stream()
                        .sorted((salvo1, salvo2)->salvo1.getId().compareTo(salvo2.getId()))
                        .map(salvo -> salvoDetail(salvo))
                        .collect(toList())

                );
        return dto;

    }

    public Map<String, Object> salvoDetail(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put(salvo.getTurn().toString(),salvo.getLocation());
        return dto;
    }






    public Map<String, Object> shipDetail(Ship ship){
        Map<String, Object>  dto = new LinkedHashMap<>();
        dto.put("type", ship.getType());
        dto.put("location", ship.getLocation());
        return  dto;
    }



    public Map<String, Object> gameplayerDto(GamePlayer gamePlayer){
        Map<String, Object>  dto = new LinkedHashMap<>();
        dto.put("Id", gamePlayer.getId());
        dto.put("player", playerDto(gamePlayer.getPlayer()));


        return dto;

    }

    public  Map<String,Object> playerDto(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());

        return dto;
    }



}


