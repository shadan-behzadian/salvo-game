package com.shadan.salvos.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.*;

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
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository , GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository){
		return (args)-> {
            Player player1 = playerRepository.save(new Player("Jack","Bauer","j.bauer@ctu.gov"));
            Player player2 = playerRepository.save(new Player("Chloe","O'Brian","shadan_b8@yahoo.com"));
            Player player3 = playerRepository.save(new Player("valeria","carbonada","valeri.carbonada@gmail.com"));
            Player player4 = playerRepository.save(new Player("nico","capo","nico.capo@yahoo.com"));
            Player player5 = playerRepository.save(new Player("Kim","Bauer","kim_bauer@gmail.com"));
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
            GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(new Date(),player2,game1));
            System.out.println("game player2" + gamePlayer2);
            GamePlayer gamePlayer3 = gamePlayerRepository.save(new GamePlayer(new Date(),player3,game2));
            System.out.println("game player3" + gamePlayer3);
			GamePlayer gamePlayer4 = gamePlayerRepository.save(new GamePlayer(new Date(),player4,game2));
			System.out.println("game player4" + gamePlayer4);
			GamePlayer gamePlayer5 = gamePlayerRepository.save(new GamePlayer(new Date(),player5,game3));
			System.out.println("game player4" + gamePlayer5);


            // the parameter is defined as a list so to add an argument here you need to specify it as 'Arrays.asList'
			Ship ship1 = shipRepository.save(new Ship("carrier", Arrays.asList("A1","A2","A3","A4","A5"),gamePlayer1));
		System.out.println("ship location is : " + ship1.getLocation());
			Ship ship2 = shipRepository.save(new Ship("battleship",Arrays.asList("B4","B5","B6","B7"),gamePlayer1));
		System.out.println(ship2);
			Ship ship3 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F3","F4"),gamePlayer1));
		System.out.println(ship3);
		Ship ship4 = shipRepository.save(new Ship("destroyer", Arrays.asList("C1","D1","E1"),gamePlayer1));
			System.out.println(ship4);
			Ship ship5 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J2","J3"),gamePlayer1));
			System.out.println(ship5);



			System.out.println(gamePlayer1.getPlayer());gamePlayer1.getToday();



			Ship ship12 = shipRepository.save(new Ship("carrier", Arrays.asList("A1","A2","A3","A4","A5"),gamePlayer2));
			System.out.println("ship location is : " + ship1.getLocation());
			Ship ship22 = shipRepository.save(new Ship("battleship",Arrays.asList("B4","B5","B6","B7"),gamePlayer2));
			System.out.println(ship2);
			Ship ship32 = shipRepository.save(new Ship("submarine", Arrays.asList("C2","C3","C4"),gamePlayer2));
			System.out.println(ship3);
			Ship ship42 = shipRepository.save(new Ship("destroyer", Arrays.asList("D5","D6","D7"),gamePlayer2));
			System.out.println(ship4);
			Ship ship52 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("E1","E2","E3"),gamePlayer2));
			System.out.println(ship5);


			Ship ship13 = shipRepository.save(new Ship("carrier", Arrays.asList("A1"),gamePlayer3));
			System.out.println("ship location is : " + ship1.getLocation());
			Ship ship23 = shipRepository.save(new Ship("battleship",Arrays.asList("B4","B5"),gamePlayer3));
			System.out.println(ship2);
			Ship ship33 = shipRepository.save(new Ship("submarine", Arrays.asList("I9","J3"),gamePlayer3));
			System.out.println(ship3);
			Ship ship43 = shipRepository.save(new Ship("destroyer", Arrays.asList("A10","B10"),gamePlayer3));
			System.out.println(ship4);
			Ship ship53 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("H4"),gamePlayer3));
			System.out.println(ship5);

			Ship ship14 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2"),gamePlayer4));

			Ship ship24 = shipRepository.save(new Ship("battleship",Arrays.asList("B4","B5"),gamePlayer4));

			Ship ship34 = shipRepository.save(new Ship("submarine", Arrays.asList("E9","F9","G9"),gamePlayer4));

			Ship ship44 = shipRepository.save(new Ship("destroyer", Arrays.asList("J4","J5"),gamePlayer4));

			Ship ship54 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("E2","E3"),gamePlayer4));

			Ship ship15 = shipRepository.save(new Ship("carrier", Arrays.asList("A1","A2","A3","A4","A5"),gamePlayer5));
			System.out.println("ship location is : " + ship1.getLocation());
			Ship ship25 = shipRepository.save(new Ship("battleship",Arrays.asList("B4","B5","B6","B7"),gamePlayer5));
			System.out.println(ship2);
			Ship ship35 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F3","F4"),gamePlayer5));
			System.out.println(ship3);
			Ship ship45 = shipRepository.save(new Ship("destroyer", Arrays.asList("C1","D1","E1"),gamePlayer5));
			System.out.println(ship4);
			Ship ship55 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J2","J3"),gamePlayer5));
			System.out.println(ship5);


			//you were saving in SalvoRepository which is not correct should be salvorepository (which is the object)
			Salvo salvo1 = salvoRepository.save(new Salvo(1,Arrays.asList("A1","A2"),gamePlayer1));
			Salvo salvo2 = salvoRepository.save(new Salvo(1,Arrays.asList("B1","C3"),gamePlayer2));
			Salvo salvo3 = salvoRepository.save(new Salvo(1,Arrays.asList("F2","E3"),gamePlayer3));
			Salvo salvo4 = salvoRepository.save(new Salvo(2,Arrays.asList("E3","C5"),gamePlayer1));
			Salvo salvo5 = salvoRepository.save(new Salvo(2,Arrays.asList("B6","G4"),gamePlayer2));
			Salvo salvo6 = salvoRepository.save(new Salvo(2,Arrays.asList("H6","G9"),gamePlayer3));
			Salvo salvo7 = salvoRepository.save(new Salvo(3,Arrays.asList("G6","F4"),gamePlayer1));
			Salvo salvo8 = salvoRepository.save(new Salvo(3,Arrays.asList("F6","H10"),gamePlayer2));
			Salvo salvo9 = salvoRepository.save(new Salvo(3,Arrays.asList("I6","J9","F2"),gamePlayer3));


		};



	}
}
