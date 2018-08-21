package com.shadan.salvos.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/*In JPA, an Entity class is equivalent to a row of a database. A Repository class is analogous to a table, i.e.,
it is a class that manages a collection of instances.
Defining a repository involves almost no code, if nothing special is being added.

 It defines a Java interface, not a class. The interface is a JpaRepository, which is in turn an extension of
 CrudRepository. CRUD stands for the most common operations all databases need to support
 Create
Read
Update
Delete*/

/*PlayerRepository is defined to be a JpaRepository that manages instances
of Player. Spring will create an actual class with code that implements this interface.*/
@RepositoryRestResource
    public interface PlayerRepository extends JpaRepository<Player, Long> {
        List<Player> findByUserName(String userName);
       Player findByFirstName(String firstName);
       Player findById(Long id);
    List<Player> findByLastName(String lastName);

}



