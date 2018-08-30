/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.BigData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import koliadenko.BigData.entities.Message;
import koliadenko.BigData.entities.Topic;
import koliadenko.BigData.entities.User;
import koliadenko.BigData.repo.MessageRepository;
import koliadenko.BigData.repo.TopicRepository;
import koliadenko.BigData.repo.UserRepository;
import static koliadenko.BigData.utils.BigTextGenerator.generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author koliadenko
 */
@Repository
public class FirstInitiator {

    Random rnd = new Random();

    @Autowired
    private UserRepository uRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MessageRepository messRepository;

    protected void initUsers(int n) {
        List<User> users = new ArrayList<>();
        users.add(new User("KoliaAdmin", new Date(), "admin", "SQUAD is big"));

        for (int i = 1; i < n; i++) {
            users.add(new User("User_" + i, new Date(), "user", "My favorite number is " + rnd.nextInt(100)));
        }

        System.out.println(uRepository.saveAll(users));
    }

    protected void initThemes() {

        List<User> users = uRepository.findAll();
        List<Topic> topics = new ArrayList<>();
        int i = 1;
        for (User u : users) {
            topics.add(new Topic("Theme " + (i++), new Date(), u));
        }
        topicRepository.saveAll(topics);
    }

    protected void createMessages() {

        int N = 2_000;

        for (int k = 0; k < 1000; k++) { //1300-1800 inserts/second
            User u = uRepository.findById(rnd.nextInt(49) + 1).get();
            Topic topic = topicRepository.findById(rnd.nextInt(49) + 1).get();

            Date date = new Date();

            List<Message> batchMes = new ArrayList<>(N);

            long t = System.nanoTime();

            for (int i = 0; i < N; i++) {
                Message mes = new Message(u, topic, generate(40) + ". " + generate(30), date);
                batchMes.add(mes);
            }
            messRepository.saveAll(batchMes); //это сохранение требует 93% всего времени ЦПУ метода createMessages()
            //провел исследование. Гигантский стек, ведущий к Хибернейтовскому прокси.
            System.out.println("===ms: " + (System.nanoTime() - t) / N / 1000_0 / 100.0);

            System.out.println("=================== Messages exist: " + messRepository.count());

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }

    }
}
