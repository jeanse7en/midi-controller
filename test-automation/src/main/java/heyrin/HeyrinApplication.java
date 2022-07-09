package heyrin;

import heyrin.repository.HrProductRepository;
import heyrin.service.HrSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootApplication
public class HeyrinApplication implements CommandLineRunner {
 
    @Autowired
    HrProductRepository repository;
    @Autowired
    HrSaleService hrSaleService;

    public static void main(String[] args) {
        SpringApplication.run(HeyrinApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        hrSaleService.createSalePage();
        repository.findAll();


    }
}