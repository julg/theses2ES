package fr.abes.theses2ES.configuration;

import fr.abes.theses2ES.dto.TheseDTO;
import fr.abes.theses2ES.processor.TheseItemProcessor;
import fr.abes.theses2ES.utils.XMLJsonMarshalling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    protected JobBuilderFactory jobs;

    @Autowired
    protected StepBuilderFactory steps;

    @Value("${job.chunk}")
    private Integer chunkSize;

    @Autowired
    private JobConfig config;

    @Autowired
    @Qualifier("theseWriteListener")
    private ItemWriteListener<TheseDTO> theseWriteListener;
    @Autowired
    @Qualifier("theseProcessListener")
    private ItemProcessListener<TheseDTO, TheseDTO> theseProcessListener;

    // ---------- JOB ---------------------------------------------

/*    @Bean
    public Job jobIndexationThesesDansES(Step stepIndexThesesDansES, JobRepository jobRepository,
            JobTheseCompletionNotificationListener listener) {
        log.info("debut du job indexation des theses dans ES...");

        return jobs.get("indexationThesesDansES").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(stepIndexThesesDansES).end().build();
    }*/

    @Bean
    public Job indexerDansEs(@Qualifier("indexerDansEsReader") ItemReader reader,
                             @Qualifier("processorThese") ItemProcessor processor,
                             @Qualifier("ESItemWriter") ItemWriter writer) {
        log.info("debut du job indexation des theses dans ES...");

        return jobs
                .get("indexerDansEs").incrementer(new RunIdIncrementer())
                .start(genericStep(reader, processor,writer))
                .build();
    }
    // ---------- STEP --------------------------------------------
/*    @Bean
    public Step stepIndexThesesDansES(@Qualifier("writerTheseDansES") ESItemWriter writerTheseDansES) {
        return steps.get("stepIndexationThese").<TheseDTO, TheseDTO>chunk(config.getChunk())
                .listener(theseWriteListener)
                .reader(databaseItemReaderThreadSafe()).processor(processorThese()).listener(theseProcessListener)
                .writer(writerTheseDansES).build();
    }*/

    private Step genericStep(ItemReader reader, ItemProcessor processor, ItemWriter writer) {
        return steps.get("genericStep").chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // ---------------- TASK EXECUTOR ----------------------------
    /*@Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }*/

    // ---------------- READER THREAD SAFE ----------------------------
    @Bean
    public ItemReader<TheseDTO> databaseItemReaderThreadSafe() {
        log.info("début du reader these thread safe...");

        try {
            return null;
            //return new JdbcCursorItemReaderBuilder<TheseDTO>().name("theseReader").dataSource(dataSourceLecture)
             //       .sql("SELECT iddoc, nnt, doc from DOCUMENT order by iddoc asc offset 0 rows fetch next 5000 rows only").rowMapper(new TheseRowMapper()).build();

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

            //queryProvider.setWhereClause("where nnt = '2007NAN21015'");
            queryProvider.setWhereClause("where rownum < " + config.getWhereLimite());
            Map<String,Order> orderKeys = new HashMap<>();
            orderKeys.put("iddoc", Order.ASCENDING);
            queryProvider.setSortKeys(orderKeys);
            //queryProvider.setWhereClause("where nnt = '2000PA010697' or nnt = '2001MNHN0022'or nnt = '2003MON30025' or nnt = '2003PA100181' or nnt = '2011AIX10218' or nnt = '2012PA010501' or nnt = '2014TOU20035' or nnt = '2014TOU20047' or nnt = '2015TOU20116' or nnt = '2020PA100137' or nnt = '2020TOU20084'");
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

    // --------------------- Utilitaires --------------------------------

    @Bean
    public XMLJsonMarshalling xmlJsonMarshalling() {
        return new XMLJsonMarshalling();
    }
}
