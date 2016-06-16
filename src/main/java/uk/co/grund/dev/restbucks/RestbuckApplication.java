/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.grund.dev.restbucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableAutoConfiguration
public class RestbuckApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestbuckApplication.class, args);
    }

    @Bean
    public CurieProvider curieProvider() {
        return new DefaultCurieProvider("rb", new UriTemplate("/restbucks.html#{rel}"));
    }
}
