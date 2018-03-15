package hello;

import hello.model.Customer;
import hello.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Customer("Jack", "Bauer", "jack_bauer", "jack123"));
            repository.save(new Customer("Chloe", "O'Brian", "chloe_obrian", "chloe123"));
            repository.save(new Customer("Kim", "Bauer", "kim_bauer", "kim123"));
            repository.save(new Customer("David", "Palmer", "david_palmer", "david123"));
            repository.save(new Customer("Michelle", "Dessler", "michelle_dessler", "michelle123"));
        };
    }
}
