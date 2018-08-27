/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData.rest;

import java.util.List;
import koliadenko.BigData.entities.Message;
import koliadenko.BigData.entities.User;
import koliadenko.BigData.repo.MessageRepository;
import koliadenko.BigData.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author koliadenko
 */
@RestController
public class MessagesController {

    @Autowired
    private MessageRepository messRepository;
    @Autowired
    private UserRepository uRepo;

    @GetMapping("/count")
    public String getCount() {
        return "All messages: " + messRepository.count();
    }

    @GetMapping("/count/{user}")
    public String getCount(@PathVariable("user") final String userName) {

        User user = uRepo.findByName(userName).get(0);

        List<Message> mess = messRepository.findByAuthor(user);

        return userName + " messages: " + mess.size()
                + "\n " + mess;
    }

}
