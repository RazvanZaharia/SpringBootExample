package hello;

import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@EnableJpaRepositories("hello.repository")
@ComponentScan(basePackages = "hello")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

   /* @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new User("Jack", "Bauer", "jack_bauer", "jack123"));
            repository.save(new User("Chloe", "O'Brian", "chloe_obrian", "chloe123"));
            repository.save(new User("Kim", "Bauer", "kim_bauer", "kim123"));
            repository.save(new User("David", "Palmer", "david_palmer", "david123"));
            repository.save(new User("Michelle", "Dessler", "michelle_dessler", "michelle123"));
        };
    }*/
}
