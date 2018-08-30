/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData.repo;

import java.util.List;
import java.util.stream.Stream;
import koliadenko.BigData.entities.Message;
import koliadenko.BigData.entities.Topic;
import koliadenko.BigData.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author koliadenko
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Long countByAuthor(User author); //общее количество сообщений у автора

    List<Message> findByAuthor(User author); //не юзай - все мессаги автора

    Stream<Message> findFirst30ByTopic(Topic topic); //первые 30 в теме

    List<Message> findFirst30ByIdGreaterThan(Integer id); //первые 30 после

    List<Message> findFirst30ByTopicAndIdGreaterThan(Topic topic, Integer id); //первые 30 после

}
