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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final UserRepository uRepository;
    private final TopicRepository topicRepository;
    private final MessageRepository messRepository;

    @Autowired
    public FirstInitiator(UserRepository uRepository, TopicRepository topicRepository, MessageRepository messRepository) {
        this.uRepository = uRepository;
        this.topicRepository = topicRepository;
        this.messRepository = messRepository;
    }

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

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //messRepository.deleteAll();
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int FULL = 29920_000;

        int THREADS = 8;
        int batch = 5000; //18к пачка на грани проблем
        int N = FULL / (THREADS * batch);


        /*
        ======================================================================================
        Этот код чуток кривой. Нельзя делать пачку в миллион сразу
        нужно делить на цикл.
        
        ======================================================================================
         */
        ExecutorService service = Executors.newFixedThreadPool(THREADS);
        final CountDownLatch cdl = new CountDownLatch(THREADS);

        long t = System.nanoTime();
        for (int k = 0; k < THREADS; k++) {
            service.execute(() -> {

                List<Message> batchMes;

                for (int z = 0; z < N; z++) {
                    batchMes = new ArrayList<>(batch);

                    User u = uRepository.findById(rnd.nextInt(49) + 1).get();
                    Topic topic = topicRepository.findById(rnd.nextInt(49) + 1).get();

                    Date date = new Date();
                    for (int i = 0; i < batch; i++) {
                        Message mes = new Message(u, topic, generate(1).toUpperCase() + generate(15) + ". " + generate(30), date);
                        batchMes.add(mes);
                    }
                    messRepository.saveAll(batchMes); //это сохранение требует 93% всего времени ЦПУ метода createMessages()
                    //провел исследование. Гигантский стек, ведущий к Хибернейтовскому прокси.

                    if (rnd.nextInt(8) == 0) { //в 8 раз реже, примерно раз на пачку
                        System.out.println("=================== Messages exist: " + messRepository.count());
                    }
                }

                cdl.countDown();

            });
        }

        try {
            cdl.await();
        } catch (InterruptedException ex) {
        }
        System.out.println((new Date()) + "===ms per insert: " + (System.nanoTime() - t) / FULL / 1000 / 1000.0);
        System.out.println("=================== Messages exist: " + messRepository.count());
        service.shutdown();
        System.exit(0);
    }
}
