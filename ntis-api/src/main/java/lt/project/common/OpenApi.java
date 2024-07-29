package lt.project.common;

import java.util.ArrayList;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition
@Configuration
public class OpenApi {

    @Value("${app.host}")
    private String hostUrl;

    @Bean
    public GroupedOpenApi openAPiConfig() {
        return GroupedOpenApi.builder().group("NTIS Public Api").pathsToMatch("/api/**").build();

    }

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        ArrayList<Server> servers = new ArrayList<>();

        servers.add(new Server().url(hostUrl + "/ntis-api/rest").description(hostUrl));
        return new OpenAPI().info(new Info().title("NTIS OpenAPI 3.0").version("1.0.0").description("NTIS API "))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)).components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .servers(servers);
    }

    // @Bean
    // public OpenApiCustomiser globalErrorResponsesCustomizer() {
    // return openAPI -> {
    // ApiResponses globalResponses = new ApiResponses();
    //
    // // Define and add global error responses
    // globalResponses.addApiResponse("Successful response", new ApiResponse().description("Successful response"));
    // globalResponses.addApiResponse("Bad request, validation failed", new ApiResponse().description("Bad request, validation failed"));
    // globalResponses.addApiResponse("Unauthorized", new ApiResponse().description("Unauthorized"));
    // globalResponses.addApiResponse("Forbidden", new ApiResponse().description("Forbidden"));
    // globalResponses.addApiResponse("Resource not found", new ApiResponse().description("Resource not found"));
    // globalResponses.addApiResponse("Internal server error", new ApiResponse().description("Internal server error"));
    //
    // openAPI.getPaths().values().forEach(pathItem -> {
    // pathItem.readOperations().forEach(operation -> {
    // // Combine the global responses with your existing @ApiResponses
    // ApiResponses operationResponses = operation.getResponses();
    // operationResponses.putAll(globalResponses);
    // });
    // });
    // };
    //
    // }
}