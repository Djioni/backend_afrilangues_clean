package api.afrilangue.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Configuration
public class Swagger {
    final String apiTitle = String.format("%s API", StringUtils.capitalize("AFRILANGUES"));

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "Authorization";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))).info(new Info().title(apiTitle).version("V1")
                        .description("Afrilangues, la plateforme dédiée exclusivement à l'apprentissage des langues africaines en ligne"));
    }

}

