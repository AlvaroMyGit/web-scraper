package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PcStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(PcStoreApplication.class, args);
    }
}
