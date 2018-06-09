package org.koliadenko.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class TestMailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subj, String text) {

        SimpleMailMessage mes = new SimpleMailMessage();
        mes.setFrom("mlecochenko@gmail.com");//3. это обязателньое поле :)))))))))))))))

        mes.setTo(to);
        mes.setSubject(subj);
        mes.setText(text);

        mailSender.send(mes);
    }

}
