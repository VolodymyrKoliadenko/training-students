/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData.thymeleaf;

import java.util.List;
import java.util.Map;
import koliadenko.BigData.entities.Message;
import koliadenko.BigData.entities.Topic;
import koliadenko.BigData.repo.MessageRepository;
import koliadenko.BigData.repo.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author koliadenko
 */
@Controller
public class TopicController {

    @Autowired
    private MessageRepository messRepository;
    @Autowired
    private TopicRepository topicRepository;

    @RequestMapping("/topic{topicId}")
    public String welcome(Map<String, Object> model, @PathVariable Integer topicId) {

        //Integer topicId = 1;
        Integer id = 0;

        Topic topic = topicRepository.findById(topicId).get();

        List<Message> messages = messRepository.findFirst30ByTopicAndIdGreaterThan(topic, id);

        model.put("messages", messages);
        model.put("lastMes", messages.get(messages.size() - 1).getId());
        return "welcome"; //вот оно главное
        // именно здесь возвращает адрес .
    }
}
