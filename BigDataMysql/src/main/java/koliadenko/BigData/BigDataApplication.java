package koliadenko.BigData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BigDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigDataApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("====================started================");
            
            //FirstInitiator initiator = ctx.getBean(FirstInitiator.class);

            //initiator.initUsers(40);
            //initiator.initThemes();
            //initiator.createMessages();
            
            //System.exit(0);
        };
    }
}
