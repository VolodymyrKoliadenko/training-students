package org.koliadenko.jms;

import java.util.Properties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableJms
@ComponentScan
public class JMSandMailConfig {

    static final String BROKER_URL = "tcp://localhost:51616";//61ххх почему то заняты, какой-то глюк в винде
    static final String BROKER_USERNAME = "system";
    static final String BROKER_PASSWORD = "manager";

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setPassword(BROKER_USERNAME);
        connectionFactory.setUserName(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(true); //включаем topic вместо queue
        //если включить, все будет топиками, если выключить - все очередями
//вариант - иметь разные фабрики @JmsListener(destination = __, containerFactory = "__")
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("1-1");
        factory.setPubSubDomain(true);//включаем topic вместо queue
        return factory;
    }

    //mail from GMAIl
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("mlecochenko@gmail.com");
        mailSender.setPassword("paasword12");

        //1.нужна настройка, потому что Гугл не пропускает простые подключения. Нужен TLS
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //Нужен TLS
        props.put("mail.debug", "true");
        //2ю нужно включать недоверенные приложения в гугле

        return mailSender;
    }
}
