package org.koliadenko.jms.alert;

import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

@Component
public class CommandNotificator {

    @Autowired
    private JmsOperations jmsOperations;

    public String sendCommand(String command) {
        jmsOperations.send("main.topic",
                (Session session) -> session.createTextMessage("The command: " + command));
        return "OK " + command;
    }

}
