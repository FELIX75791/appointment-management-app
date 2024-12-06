package org.dljl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // Allow CORS for all endpoints
                .allowedOrigins(
                        "http://localhost:3000", // Local development
                        "https://appt-mgmt-frontend.firebaseapp.com", // Firebase Hosting
                        "https://appt-mgmt-frontend.web.app",
                        "https://private-service-455124587991.us-central1.run.app" // Additional domain if needed
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials like cookies or headers
      }
    };
  }
}