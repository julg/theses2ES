package fr.abes.theses2ES.notification;

import fr.abes.theses2ES.configuration.ElasticConfig;
import fr.abes.theses2ES.writer.ElasticClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JobTheseCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    private long start;

    @Autowired
    private ElasticConfig elasticConfig;

    @Autowired
    public JobTheseCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Load elastic client");
        try {
            ElasticClient.chargeClient(elasticConfig.getHostname(), elasticConfig.getPort(), elasticConfig.getScheme(), elasticConfig.getUserName(), elasticConfig.getPassword(), elasticConfig.getProtocol());
        } catch (Exception e) {
            log.error("pb lors du chargement du client ES : " + e.toString());
            throw new RuntimeException(e);
        }
        log.info("Debut du job d'indexation des theses");
        start = System.currentTimeMillis();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long duration = System.currentTimeMillis() - start;

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("job indexation des theses termine");
        }

        log.info(millisecondsToReadeable(duration));
    }

    private String millisecondsToReadeable(long millis) {

        long hour = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hour);
        long min = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(min);
        long sec = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(sec);

        if (hour > 0) {
            return String.format("%02d h, %02d min, %02d sec, %03d millis", hour, min, sec, millis);
        } else {
            if (min > 0) {
                return String.format("%02d min, %02d sec, %03d millis", min, sec, millis);
            } else {
                return String.format("%02d sec, %03d millis", sec, millis);
            }
        }
    }
}
