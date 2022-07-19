package heyrin;

import heyrin.repository.HrProductRepository;
import heyrin.service.HrSaleService;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class HeyrinApplication implements CommandLineRunner {
 
    @Autowired
    HrProductRepository repository;
    @Autowired
    HrSaleService hrSaleService;

    public static void main(String[] args) {
        SpringApplication.run(HeyrinApplication.class, args);
    }

    @PostConstruct
    private void init() {
        try {
            Properties p = new Properties();
            p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            Velocity.init(p);
        } catch (Exception e) {
            System.out.println("Failed to initial velocity");
        }
    }

    @Override
    public void run(String... args) throws IOException {
        hrSaleService.createHomePage();
        hrSaleService.createSalePage();
        hrSaleService.createCatalogPage();
        hrSaleService.createDetailProducts();


    }
}