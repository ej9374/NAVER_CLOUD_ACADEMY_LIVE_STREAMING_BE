package naver_cloud.live.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Live Streaming Swagger")
                        .description("라이브 스트리밍 방송 스웨거 문서")
                        .version("1.0.0"));
    }
}
