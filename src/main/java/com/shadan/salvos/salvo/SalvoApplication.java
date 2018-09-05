package com.shadan.salvos.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.*;

@SpringBootApplication
/* this annotation, among many other things, tells Spring to look for cases in the code
that need instances of bean.
 */

public class SalvoApplication   {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	//annotation to mark a method that returns an instance of a Java bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository , GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository,ScoreRepository scoreRepository){
		return (args)-> {
            Player player1 = playerRepository.save(new Player("Jack","Bauer","j.bauer@ctu.gov","24"));
            Player player2 = playerRepository.save(new Player("Chloe","O'Brian","shadan_b8@yahoo.com","42"));
			Player player3 = playerRepository.save(new Player("Kim","Bauer","kim_bauer@gmail.com","kb"));
			Player player4 = playerRepository.save(new Player("Tony","almeida","t.almeida@ctu.gov","mole"));
			Player player5 = playerRepository.save(new Player("valeria","carbonada","valeri.carbonada@gmail.com","val"));
			Player player6 = playerRepository.save(new Player("nico","capo","nico.capo@yahoo.com","nic"));

			Player players =  playerRepository.findByUserName("shadan");
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
            Game game4 = gameRepository.save(new Game(new Date()));
            Game game5 = gameRepository.save(new Game(new Date()));
            Game game6 = gameRepository.save(new Game(new Date()));
            Game game7 = gameRepository.save(new Game(new Date()));
            Game game8 = gameRepository.save(new Game(new Date()));
            //using methods in repository
            List<Game> allTheGames = gameRepository.findAll();
            System.out.println(allTheGames);




            GamePlayer gamePlayer1 = gamePlayerRepository.save(new GamePlayer(new Date(),player1,game1));
            System.out.println("game player1" + gamePlayer1.getId());
            GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(new Date(),player2,game1));
            System.out.println("game player2" + gamePlayer2);
            GamePlayer gamePlayer3 = gamePlayerRepository.save(new GamePlayer(new Date(),player1,game2));
            System.out.println("game player3" + gamePlayer3);
			GamePlayer gamePlayer4 = gamePlayerRepository.save(new GamePlayer(new Date(),player2,game2));
			System.out.println("game player4" + gamePlayer4);
			GamePlayer gamePlayer5 = gamePlayerRepository.save(new GamePlayer(new Date(),player2,game3));
			System.out.println("game player4" + gamePlayer5);
			GamePlayer gamePlayer6 = gamePlayerRepository.save(new GamePlayer(new Date(),player4,game3));
			GamePlayer gamePlayer7 = gamePlayerRepository.save(new GamePlayer(new Date(),player2,game4));
			GamePlayer gamePlayer8 = gamePlayerRepository.save(new GamePlayer(new Date(),player1,game4));
			GamePlayer gamePlayer9 = gamePlayerRepository.save(new GamePlayer(new Date(),player4,game5));
			GamePlayer gamePlayer10 = gamePlayerRepository.save(new GamePlayer(new Date(),player1,game5));
			GamePlayer gamePlayer11 = gamePlayerRepository.save(new GamePlayer(new Date(),player3,game6));
			GamePlayer gamePlayer12 = gamePlayerRepository.save(new GamePlayer(new Date(),player4,game7));
			GamePlayer gamePlayer13 = gamePlayerRepository.save(new GamePlayer(new Date(),player3,game8));
			GamePlayer gamePlayer14 = gamePlayerRepository.save(new GamePlayer(new Date(),player4,game8));




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


			Ship ship16 = shipRepository.save(new Ship("carrier", Arrays.asList("A1","A2","A3","A4"),gamePlayer6));
			System.out.println("ship location is : " + ship1.getLocation());
			Ship ship26 = shipRepository.save(new Ship("battleship",Arrays.asList("B4","B5","B6","B7"),gamePlayer6));
			System.out.println(ship2);
			Ship ship36 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer6));
			System.out.println(ship3);
			Ship ship46 = shipRepository.save(new Ship("destroyer", Arrays.asList("C1","D1","E1"),gamePlayer6));
			System.out.println(ship4);
			Ship ship56 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer6));
			System.out.println(ship5);

			Ship ship17 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer7));
			Ship ship27 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer7));
			Ship ship37 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer7));
			Ship ship47 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer7));
			Ship ship57 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer7));


			Ship ship18 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer8));
			Ship ship28 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer8));
			Ship ship38 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer8));
			Ship ship48 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer8));
			Ship ship58 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer8));


			Ship ship19 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer9));
			Ship ship29 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer9));
			Ship ship39 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer9));
			Ship ship49 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer9));
			Ship ship59 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer9));


			Ship ship110 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3"),gamePlayer10));
			Ship ship211 = shipRepository.save(new Ship("battleship",Arrays.asList("D5","D6","D7"),gamePlayer10));
			Ship ship312 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer10));
			Ship ship413 = shipRepository.save(new Ship("destroyer", Arrays.asList("D2","E2"),gamePlayer10));
			Ship ship514 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer10));


			Ship ship115 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer11));
			Ship ship215 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer11));
			Ship ship315 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer12));
			Ship ship416 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer13));
			Ship ship517 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer14));



			Ship ship118 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer12));
			Ship ship218 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer12));
			Ship ship318 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer12));
			Ship ship418 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer12));
			Ship ship518 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer12));


			Ship ship119 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer13));
			Ship ship219 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer13));
			Ship ship319 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer13));
			Ship ship419 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer13));
			Ship ship519 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer13));



			Ship ship120 = shipRepository.save(new Ship("carrier", Arrays.asList("G1","G2","G3","G4"),gamePlayer14));
			Ship ship220 = shipRepository.save(new Ship("battleship",Arrays.asList("D4","D5","D6","D7"),gamePlayer14));
			Ship ship320 = shipRepository.save(new Ship("submarine", Arrays.asList("F2","F4"),gamePlayer14));
			Ship ship420 = shipRepository.save(new Ship("destroyer", Arrays.asList("C2","D2","E2"),gamePlayer14));
			Ship ship520 = shipRepository.save(new Ship("patrolBoat", Arrays.asList("J1","J3"),gamePlayer14));






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


			Score score1 = scoreRepository.save(new Score(1,new Date(),game1,player1));
			Score score2 = scoreRepository.save(new Score(0.5,new Date(),game1,player2));
			Score score3 = scoreRepository.save(new Score(0.5,new Date(),game2,player3));
			Score score4 = scoreRepository.save(new Score(0,new Date(),game2,player4));
			Score score5 = scoreRepository.save(new Score(1,new Date(),game3,player5));
			Score score6 = scoreRepository.save(new Score(0,new Date(),game3,player6));
			Score score7 = scoreRepository.save(new Score(0.5,new Date(),game4,player4));
			Score score8 = scoreRepository.save(new Score(0.5,new Date(),game4,player1));




		};



	}
}


//Now, to tell Spring to use this database (repository) for
// authentication, we add the following WebSecurityConfiguration
// class to our Application.java file.
//This configuration class definition goes outside
// and after the definition of our Application class. Java allows
// a file to have more than one class definition, as long as only one
// class is public, and the public class has the same name as the file.
// The other classes are included in the package but not publicly
// accessible. Spring can find them because of the @Configuration annotation.
//The job of this new class is to take the name
// someone has entered for log in, search the database with that name, and return a
// UserDetails object with name, password, and role information
// for that user, if any.


//to give a role to anyone who signs in
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		//As for the userDetailsService() method, it sets
		// up an in-memory user store with a single user. That
		// user is given a username
		// of "user", a password of "password", and a role of "USER".
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserName(inputName);
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));

			}
			//you could say here if(player == shadan_b8@yahoo.com){
			//return new User(player.getUserName(), player.getPassword(),
			//						AuthorityUtils.createAuthorityList("ADMIN"));
		//}
			else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}


}

//to give a pathway to each of the roles
//if it is a user do this, if it is an admin do this....
//WebSecurityConfigurerAdapter has methods that does the security

//The WebSecurityConfig class is annotated with @EnableWebSecurity
// to enable Spring Security’s web security support and provide
// the Spring MVC integration. It also extends WebSecurityConfigurerAdapter
// and overrides a couple of its methods
// to set some specifics of the web security configuration.
@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	//The configure(HttpSecurity) method defines which URL paths
	// should be secured and which should not. Specifically,
	// the "/" and "/home" paths are configured to not require
	// any authentication. All other paths must be authenticated.
	protected void configure(HttpSecurity http) throws Exception {
		http
				//add all the pathways, notice that css here should be deferent if
				//you put all the css in one the if you give permission somewhere and
				//not in another place it wont work
				//first specify the ones that are more strict
				.authorizeRequests()
				.antMatchers("/web/game.html").hasAuthority("USER")
				.antMatchers("/web/game.js").hasAuthority("USER")
				.antMatchers("/web/game.css").hasAuthority("USER")
				.antMatchers("/web/games.html").permitAll()
				.antMatchers("/web/games.js").permitAll()
				.antMatchers("/web/games.css").permitAll()
				.antMatchers("/api/games").permitAll()
				.anyRequest().fullyAuthenticated();
		http.formLogin()
				//you use these names in fetch
				.usernameParameter("email")
				.passwordParameter("password")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();
		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}