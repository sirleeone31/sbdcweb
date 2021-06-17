package com.sbdc.sbdcweb;

import com.sbdc.sbdcweb.config.ApplicationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbdcwebApplication implements CommandLineRunner {

    private ApplicationProperties applicationProperties;

    public SbdcwebApplication(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SbdcwebApplication.class);
        app.run();
    }

    public void run(String... args) throws Exception {
        System.out.println("==============================  [Front API]  ==============================");

        if (applicationProperties.getFrontServerAPI().equals("http://localhost:4200")) {
            System.out.println("==============================  -DevServer-  ==============================");
        }

        System.out.println("===========================  " + applicationProperties.getFrontServerAPI() + "  ===========================");
        System.out.println("=========================================================================");
        System.out.println("=========================================================================");
    }
}
