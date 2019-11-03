package fr.abes.theses2ES.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("job")
@EnableBatchProcessing
public class JobConfig {

    private int chunk;

    private int throttle;

    private int whereLimite;

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
    }

    public int getWhereLimite() {
        return whereLimite;
    }

    public void setWhereLimite(int whereLimite) {
        this.whereLimite = whereLimite;
    }

}
