package org.koliadenko.jms.alert;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AlertLoggerListener {

    @Autowired
    private Logger logger;

    @JmsListener(destination = "main.topic")
    public void logMessage(final Message message) throws JMSException {

        logger.info("In main topic: "
                + ((TextMessage) message).getText());
    }

}
