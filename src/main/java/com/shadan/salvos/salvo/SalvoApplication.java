package com.shadan.salvos.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
/* this annotation, among many other things, tells Spring to look for cases in the code
that need instances of bean.
 */

public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	//annotation to mark a method that returns an instance of a Java bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository , GamePlayerRepository gamePlayerRepository){
		return (args)-> {
            Player player1 = playerRepository.save(new Player("Jack","Bauer","j.bauer@ctu.gov"));
            Player player2 = playerRepository.save(new Player("Chloe","O'Brian","shadan_b8@yahoo.com"));
            Player player3 = playerRepository.save(new Player("valeria","carbonada","valeri.carbonada@gmail.com"));
            Player player4 = playerRepository.save(new Player("nico","capo","nico.capo@yahoo.com"));
            Player player5 = playerRepository.save(new Player("Kim","Bauer","kim_bauer@gmail.com k"));
            Player player6 = playerRepository.save(new Player("Tony","capo","t.almeida@ctu.gov"));

			List<Player> players =  playerRepository.findByUserName("shadan");
			System.out.println(players);
			Player p1 =  playerRepository.findByFirstName("nico");
			System.out.println(p1);
			Player p1ByID =  playerRepository.findById(p1.getId());
			System.out.println("PlayerById: "+ p1ByID);
			//to geneate an id for a player to use it
			Long p1Id = Long.valueOf(2);
			Player playerById =  playerRepository.findById(p1Id);
			System.out.println("PlayerById: "+playerById);
			Long p2Id =Long.valueOf(5);
			Player playerById1 =  playerRepository.findById(p2Id);
			System.out.println(" Player By Id : "+playerById1);

			//findall and count are methos of repository there are more methos in the e-book
			List<Player> allPlayers =  playerRepository.findAll();
			System.out.println(allPlayers);
			Long countPlayers = playerRepository.count();
			System.out.println(countPlayers);

			List<Player> playerLastNameRepeated =  playerRepository.findByLastName("capo");
            System.out.println("the repeated ones are :" + playerLastNameRepeated);

           //from ebook: date.toInstant() when called with a date will return an Instant for the date
            Game game1 = gameRepository.save(new Game(new Date()));

            //game1.setToday(new Date());
            Game game2 = gameRepository.save(new Game(Date.from(game1.getToday().toInstant().plusSeconds(3600))));
            Game game3 = gameRepository.save(new Game(Date.from(game2.getToday().toInstant().plusSeconds(3600))));
            //using methods in repository
            List<Game> allTheGames = gameRepository.findAll();
            System.out.println(allTheGames);




            GamePlayer gamePlayer1 = gamePlayerRepository.save(new GamePlayer(new Date(),player1,game1));
            System.out.println("game player1" + gamePlayer1.getId());
            GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(new Date(),player2,game2));
            System.out.println("game player2" + gamePlayer2);
            GamePlayer gamePlayer3 = gamePlayerRepository.save(new GamePlayer(new Date(),player3,game3));
            System.out.println("game player3" + gamePlayer3);




		};



	}
}
