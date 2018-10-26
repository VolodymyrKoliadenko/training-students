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
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author koliadenko
 */
@Singleton
@Startup
public class SingletonInit {

    @PersistenceContext
    private EntityManager em;

    Random rnd = new Random();

    @PostConstruct
    //@Transactional
    public void init() {

        //Long i = em.createQuery("select count(m.id) from Message m", Long.class).getSingleResult();
        //System.out.println("=== " + i);
        User u = em.createNamedQuery("User.findById", User.class).setParameter("id", rnd.nextInt(49) + 1)
                .setMaxResults(1).getSingleResult();

        System.out.println(u);

        Topic topic = em.createNamedQuery("Topic.findById", Topic.class).setParameter("id", rnd.nextInt(49) + 1)
                .setMaxResults(1).getSingleResult();

        int N = 40_000;
        Date date = new Date();
        List<Message> batch = new ArrayList<>(N);

        for (int i = 0; i < N; i++) {
            batch.add(new Message(u, topic, "Sample little text with 9999999 letters " + i, date));
        }
        System.out.println(System.nanoTime());
        long t = System.nanoTime();
        save(batch, u, topic, date);
        System.out.println(" === high speed  mks=" + (System.nanoTime() - t) / N / 1e3);
    }

    @Transactional
    public void save(List<Message> list, User u, Topic topic, Date date) {
        for (int i = 0; i < list.size(); i++) {
            Message mes = new Message(u, topic, "Sample little text with 9999999 letters " + i, date);
            em.persist(mes);
        }
        System.out.println(System.nanoTime());
    }

}
