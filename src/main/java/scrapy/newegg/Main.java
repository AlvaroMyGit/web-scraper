package scrapy.newegg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import scrapy.newegg.scraper.cpu.CpuUrlFetcher;
import scrapy.newegg.scraper.cpu.CpuUrlProcessor;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = "scrapy.newegg")
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        // Get instances of CpuUrlFetcher and CpuUrlProcessor from the Spring context
        CpuUrlFetcher cpuUrlFetcher = context.getBean(CpuUrlFetcher.class);
        CpuUrlProcessor cpuUrlProcessor = context.getBean(CpuUrlProcessor.class);

        // Create an ExecutorService for concurrent execution
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit tasks to the ExecutorService
        executorService.submit(cpuUrlFetcher::fetchProductUrls);

        Thread.sleep(Duration.ofSeconds(1L));
        executorService.submit(cpuUrlProcessor::startProcessing);

        // Shut down the ExecutorService gracefully
        executorService.shutdown();
    }
}
