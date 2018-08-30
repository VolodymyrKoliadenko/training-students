/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData.rest;

import java.util.List;
import static java.util.stream.Collectors.joining;
import koliadenko.BigData.entities.Message;
import koliadenko.BigData.entities.Topic;
import koliadenko.BigData.entities.User;
import koliadenko.BigData.repo.MessageRepository;
import koliadenko.BigData.repo.TopicRepository;
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
    @Autowired
    private TopicRepository topicRepository;

    @GetMapping(path = "/count", produces = "text/html") //все колво
    public String getCount() {

        String retList = "";

        for (User user : uRepo.findAll()) { //это для юзера

            //List<Message> mess = messRepository.findByAuthor(user); //это если все 600к
            retList += user.getName() + " has messages: " + messRepository.countByAuthor(user)
                    + "<br> ";
        }

        return "All messages: " + messRepository.count()
                + "<br>" + retList;
    }

    @GetMapping(path = "/count/{user}") //колво конкретного user
    public String getCount(@PathVariable("user") final String userName) {

        long temp = System.nanoTime();

        User user = uRepo.findByName(userName).get(0); //это для юзера

        //List<Message> mess = messRepository.findByAuthor(user); //это если все 600к
        return userName + " has messages: " + messRepository.countByAuthor(user)
                + "\n milisec: " + (System.nanoTime() - temp) / 1000 / 1000.0;

    }

    /*@GetMapping(path = "/topic/{id}", produces = "text/html")//, produces = "text/html"
    public String getMesInTopic(@PathVariable("id") final int topicId) {

        Topic topic = topicRepository.findById(topicId).get();

        return messRepository.findFirst30ByTopic(topic).map(Message::toString)
                .collect(joining("</br>"));

    }*/

    //
    // http://localhost/test/1/1
    //
    @GetMapping(path = "/test/{topic}/{id}", produces = "text/html")
    public String getById(@PathVariable("topic") final Integer topicId, @PathVariable("id") final Integer id) {
        Topic topic = topicRepository.findById(topicId).get();

        List<Message> messages = messRepository.findFirst30ByTopicAndIdGreaterThan(topic, id);

        Integer last = messages.get(messages.size() - 1).getId();

        return "<b>" + topic.getName() + "</b></br>"
                + messages.stream().map(Message::toString)
                        .collect(joining("</br>"))
                + "</br><a href='/test/" + topicId + "/" + last + "'>Next page</a>";
    }

}
