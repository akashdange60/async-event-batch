package com.async_event.batch.scheduler;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.async_event.model.MetricsMailAudit;
import com.async_event.service.MetricsAuditService;
import com.async_event.serviceImpl.MetricsService;
import com.async_event.util.CsvUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;

@Slf4j
@Component
public class MetricsMailScheduler {
	
	private final JavaMailSender mailSender;
    private final MetricsService metricsService;
    @Autowired
    private MetricsAuditService metricsAuditService;

    @Value("${metrics.mail.to}")
    private String toEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MetricsMailScheduler(JavaMailSender mailSender,
                                MetricsService metricsService) {
        this.mailSender = mailSender;
        this.metricsService = metricsService;
    }
   
    

    @Scheduled(cron = "${metrics.mail.cron:0 0 9 * * ?}") // default 9 AM
 //In application.properties, I have mention time every 1 minutes so mail will get every minute for testing
    public void sendMetricsMail() {
    	
    	MetricsMailAudit audit = new MetricsMailAudit();
        audit.setSendTo(new String[]{
        	    "ppardakhe123@gmail.com",
        	    "akashdange60@gmail.com",
        	    "aniketchaudhari71@gmail.com"
        	});
        audit.setSendFrom(fromEmail);
        audit.setMetricsTime(LocalDateTime.now());
        

        try {
            String jsonMetrics = metricsService.fetchJvmMetrics();
//            String csvMetrics=metricsService.fetchCsvMetrics();
            
            Map<String, Object> csvMetricsMap=metricsService.fetchCsvMetrics();
            ObjectMapper objectMapper = new ObjectMapper();
         // Convert Map → JSON string
         String csvMetrics = objectMapper.writeValueAsString(csvMetricsMap);
         
         String jsonFileName = "metrics-" + LocalDate.now() + ".json";
         String csvFileName  = "metrics-" + LocalDate.now() + ".csv";
         
         byte[] jsonBytes = jsonMetrics.getBytes(StandardCharsets.UTF_8);
         byte[] csvBytes  = csvMetrics.getBytes(StandardCharsets.UTF_8);

         audit.setJsonFileName(jsonFileName);
         audit.setJsonContentType("application/json");
         audit.setJsonMetrics(jsonBytes);

         audit.setCsvFileName(csvFileName);
         audit.setCsvContentType("text/csv");
         audit.setCsvMetrics(csvBytes);
         
         audit.setStatus("SUCCESS");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom(fromEmail);
          //  helper.setTo(toEmail);
            helper.setTo(new String[]{
            	    "ppardakhe123@gmail.com",
            	    "akashdange60@gmail.com",
            	    "aniketchaudhari71@gmail.com"
            	});
            helper.setSubject("Attached are JVM metrics in JSON and CSV formats");
            helper.setText(
                    "Hi Team,\n\nPlease find today's JVM metrics attached.\n\nRegards,\nMonitoring Service"
                  
            );
         // JSON attachment (existing)
            helper.addAttachment(
            		jsonFileName,
                  //  new ByteArrayResource(metricsJson.getBytes(StandardCharsets.UTF_8))
                    new ByteArrayResource(jsonBytes),
                    "application/json"
            );
            
         // CSV attachment (NEW)
            helper.addAttachment(
            		csvFileName,
                new ByteArrayResource(csvBytes),
                "text/csv"
            );
            
            
            mailSender.send(message);
            log.info("✅ JVM metrics mail sent successfully");

        } catch (Exception ex) {
        	audit.setStatus("FAILED");
        	audit.setErrorMessage(ex.getMessage());
            log.error("❌ Failed to send JVM metrics mail", ex);
        }
        finally {
            metricsAuditService.save(audit);
        }
    }
    
    /*
    
    @Scheduled(cron = "${metrics.mail.cron}")
    public void sendMetricsMailNew() {

        try {
            Map<String, Object> metrics = metricsService.collectMetrics();

            String csvContent = CsvUtil.toCsv(metrics);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Daily Application JVM Metrics (CSV)");
            helper.setText("""
                    Hi Team,

                    Please find attached the daily JVM & Thread metrics
                    for this application.

                    Regards,
                    Monitoring Service
                    """);

            helper.addAttachment(
                    "jvm-metrics.csv",
                    new ByteArrayResource(csvContent.getBytes(StandardCharsets.UTF_8))
            );

            mailSender.send(message);
            log.info("✅ JVM metrics CSV mail sent");

        } catch (Exception e) {
            log.error("❌ Failed to send metrics mail", e);
        }
    }
*/
    
    

}
