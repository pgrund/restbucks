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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableAutoConfiguration
@EnableSwagger2
public class RestbuckApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestbuckApplication.class, args);
    }

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Bean
    public CurieProvider curieProvider() {
        return new DefaultCurieProvider("rb", new UriTemplate("/rels/{rel}"));
    }

    @Bean
    public Docket restbuckApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("restbucks")
                .apiInfo(new ApiInfoBuilder()
                        .title("RESTBucks API")
                        .description("RESTfull Coffee Ordering")
                        .contact("Peter Grund")
                        .version("2.0")
                        .build())
                .select()
                .paths(PathSelectors.regex("/orders.*"))
                .build();
    }
}
