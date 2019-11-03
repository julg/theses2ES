package fr.abes.theses2ES.configuration;

import fr.abes.theses2ES.dto.TheseDTO;
import fr.abes.theses2ES.dto.TheseRowMapper;
import fr.abes.theses2ES.notification.JobTheseCompletionNotificationListener;
import fr.abes.theses2ES.processor.TheseItemProcessor;
import fr.abes.theses2ES.utils.XMLJsonMarshalling;
import fr.abes.theses2ES.writer.ESItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    protected JobBuilderFactory jobs;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dataSourceLecture")
    protected DataSource dataSourceLecture;

    @Autowired
    private JobConfig config;

    @Autowired
    @Qualifier("theseWriteListener")
    private ItemWriteListener<TheseDTO> theseWriteListener;
    @Autowired
    @Qualifier("theseProcessListener")
    private ItemProcessListener<TheseDTO, TheseDTO> theseProcessListener;

    // ---------- JOB ---------------------------------------------

    @Bean
    public Job jobIndexationThesesDansES(Step stepIndexThesesDansES, JobRepository jobRepository,
            JobTheseCompletionNotificationListener listener) {
        log.info("debut du job indexation des theses dans ES...");

        return jobs.get("indexationThesesDansES").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(stepIndexThesesDansES).end().build();
    }

    // ---------- STEP --------------------------------------------
    @Bean
    public Step stepIndexThesesDansES(@Qualifier("writerTheseDansES") ESItemWriter writerTheseDansES) {
        return stepBuilderFactory.get("stepIndexationThese").<TheseDTO, TheseDTO>chunk(config.getChunk())
                .faultTolerant().skip(Exception.class).skipLimit(500000).listener(theseWriteListener)
                .reader(databaseItemReaderThreadSafe()).processor(processorThese()).listener(theseProcessListener)
                .writer(writerTheseDansES).taskExecutor(taskExecutor()).throttleLimit(config.getThrottle()).build();
    }

    // ---------------- TASK EXECUTOR ----------------------------
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    // ---------------- READER THREAD SAFE ----------------------------
    @Bean
    public ItemReader<TheseDTO> databaseItemReaderThreadSafe() {
        log.info("début du reader these thread safe...");

        try {
            return new JdbcPagingItemReaderBuilder<TheseDTO>().name("theseReader").dataSource(dataSourceLecture)
                    .queryProvider(createQueryProvider()).rowMapper(new TheseRowMapper()).pageSize(config.getChunk())
                    .build();

        } catch (Exception e) {
            log.error("erreur lors de la creation du JdbcPagingItemReader : " + e);
            return null;
        }
    }

    private PagingQueryProvider createQueryProvider() {
        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT iddoc, nnt, doc");
        queryProvider.setFromClause("from DOCUMENT");
        if (config.getWhereLimite() > 0) {
            queryProvider.setWhereClause("where rownum < " + config.getWhereLimite());
        }
        queryProvider.setSortKeys(sortByIdAsc());
        return queryProvider;
    }

    private Map<String, Order> sortByIdAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("iddoc", Order.ASCENDING);
        return sortConfiguration;
    }

    // -------------- PROCESSOR -------------------------------
    @Bean
    public ItemProcessor<TheseDTO, TheseDTO> processorThese() {
        return new TheseItemProcessor();
    }

    // ----------------- WRITER -------------------------------------
    @Bean
    @Qualifier("writerTheseDansES")
    public ESItemWriter writerTheseDansES() {
        return new ESItemWriter();
    }

    // --------------------- Utilitaires --------------------------------

    @Bean
    public XMLJsonMarshalling xmlJsonMarshalling() {
        return new XMLJsonMarshalling();
    }
}
