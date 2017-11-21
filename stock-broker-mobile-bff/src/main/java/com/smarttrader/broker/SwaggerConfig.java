package com.smarttrader.broker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        // custom response messages
        List<ResponseMessage> responseMessageBuilders = new ArrayList<>();
        // 500
        responseMessageBuilders.add(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Status Code 500")
                        .responseModel(new ModelRef("Error"))
                        .build()
        );
        // 403
        responseMessageBuilders.add(
                new ResponseMessageBuilder()
                        .code(403)
                        .message("Status Code 403")
                        .responseModel(new ModelRef("Forbidden"))
                        .build()
        );

        // To enable a select number of endpoints to the API
        // Register custom message builders to the Docket object
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, responseMessageBuilders)
                .globalResponseMessage(RequestMethod.GET, responseMessageBuilders);
    }

    /**
     * @return
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Title",
                "Description",
                "Version",
                "Terms of service URL",
                "Contact name",
                "License",
                "License URL"
        );
    }

}
