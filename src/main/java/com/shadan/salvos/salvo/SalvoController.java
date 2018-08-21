package com.shadan.salvos.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

//second step in plan of attack to add more details in the object
    //we ar getting the info of the game first getting ID and date then through game we access de data of gameplayers
    //(remmember it was a onetomany relationship so you need to loop aka stream over it) then through the gameplayer we want
    //to access the player info (remember it was a manytoone relationship, so each gameplayer can only have one player so
    //you dont need to stream anymore to get the data of the player.
    @RequestMapping("/games")
    public Map<String, Object> toDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        List<Game> games = gameRepository.findAll();
        dto.put("Games", games
                .stream()
                .map(game-> gameDto(game))
        .collect(toList()));
        return dto;
    }

    public  Map<String,Object> gameDto(Game game){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",game.getId());
        dto.put("date",game.getToday());

        dto.put("gamePlayers", game.getGameplayers()
                .stream()
                .map(gamePlayer -> gamePlayerDto(gamePlayer))
                .collect(toList()));


        return dto;
    }

    public  Map<String,Object> gamePlayerDto(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",gamePlayer.getId());
        dto.put("player", playerDto(gamePlayer.getPlayer()));

return dto;
    }

    public  Map<String,Object> playerDto(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());

        return dto;
    }


//first step of plan of attack to get only ID
//    public List<Long> getListOfGamesId(){
//
//        List<Game> games = new ArrayList<>();
//        games = gameRepository
//                .findAll()
//                .stream()
//                .map(b -> b.getId())
//                .collect(toList());
//
//        return games;
//    }


}










