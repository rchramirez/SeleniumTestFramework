package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:aliexpress.properties")}
)
@ComponentScan(basePackages = {"component", "pages"})
public class ApplicationContextConfig {
}
