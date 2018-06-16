package old.bad;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Это бесполезный класс. Message-Driven POJO Был еще во времена 2 Спринга,
 * управлялся ХМЛ-конфигами
 *
 */
public class ExampleListener implements MessageListener {

    /**
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
