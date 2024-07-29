package lt.project.common.boot;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NtisBootApp {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NtisBootApp.class, args);
    }
}