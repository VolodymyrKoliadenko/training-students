package org.koliadenko.jms.alert;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.koliadenko.jms.TestMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerSender {

    @Autowired
    private TestMailSender sender;

    @JmsListener(destination = "main.topic")
    public void receiveTopicMessage(final Message message) throws JMSException {

        String text = "Привет, тебе сообщение от ДЖАВЫ: <br>"
                + ((TextMessage) message).getText();

        sender.send("vovakk@mail.ru", "jms allert", text);

    }

}
