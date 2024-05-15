package com.example.webclientflora;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n..:: Flora Application ::..\n");
        Menu.menu();

    }
}
