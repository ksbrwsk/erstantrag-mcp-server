package de.hdi.erstantragmcpserver;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ErstantragIbanMcpServerApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ErstantragIbanMcpServerApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @Bean
    public RestClient.Builder getRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public ToolCallbackProvider ibanTools(IbanService ibanService) {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(ibanService)
                .build();
    }
}
