package org.tctalent.anonymization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TcApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcApiServiceApplication.class, args);

        //TODO JC Trigger login to server (need app login - ie no MFA)
        //TODO JC Trigger query of all candidates
    }

}
