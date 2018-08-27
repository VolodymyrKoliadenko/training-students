/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData.repo;

import java.util.List;
import koliadenko.BigData.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author koliadenko
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);

}
