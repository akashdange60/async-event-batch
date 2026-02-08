package com.async_event.serviceImpl;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class MetricsService {
	
	//This will provide json file with only jvm related 
	 private final RestTemplate restTemplate = new RestTemplate();

	    public String fetchJvmMetrics() {
	        String url = "http://localhost:8080/actuator/metrics/jvm.memory.used";
	        return restTemplate.getForObject(url, String.class);
	    }
	    
	    private final MeterRegistry meterRegistry;
	    
	    public MetricsService(MeterRegistry meterRegistry) {
	        this.meterRegistry = meterRegistry;
	    }
	    /*		 
 //This will provide csv file with total application metrics 	    
	    public String fetchCsvMetrics() {

	        StringBuilder csv = new StringBuilder();
	        csv.append("Metric,Value\n");

	        csv.append("jvm.memory.used,")
	           .append(meterRegistry.get("jvm.memory.used").gauge().value())
	           .append("\n");

	        csv.append("jvm.memory.max,")
	           .append(meterRegistry.get("jvm.memory.max").gauge().value())
	           .append("\n");

	        csv.append("jvm.threads.live,")
	           .append(meterRegistry.get("jvm.threads.live").gauge().value())
	           .append("\n");

	        csv.append("jvm.threads.daemon,")
	           .append(meterRegistry.get("jvm.threads.daemon").gauge().value())
	           .append("\n");

	        csv.append("jvm.threads.peak,")
	           .append(meterRegistry.get("jvm.threads.peak").gauge().value())
	           .append("\n");

	        return csv.toString();
	    }   
    */	
	    
 //This will provide csv file with total application metrics 
	    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	    
	    public Map<String, Object> fetchCsvMetrics() {

	        Map<String, Object> metrics = new LinkedHashMap<>();

	        // JVM Memory
	        metrics.put("jvm.memory.used",meterRegistry.get("jvm.memory.used").gauge().value());
	        metrics.put("jvm.memory.max",meterRegistry.get("jvm.memory.max").gauge().value());

	        // JVM Threads (Micrometer)
	        metrics.put("jvm.threads.live",meterRegistry.get("jvm.threads.live").gauge().value());
	        metrics.put("jvm.threads.daemon",meterRegistry.get("jvm.threads.daemon").gauge().value());
	        metrics.put("jvm.threads.peak",meterRegistry.get("jvm.threads.peak").gauge().value());

	        // JVM Threads (MXBean – VERY IMPORTANT)
	        metrics.put("threads.total.started",threadMXBean.getTotalStartedThreadCount());
	        metrics.put("threads.current",threadMXBean.getThreadCount());
	        metrics.put("threads.peak",threadMXBean.getPeakThreadCount());
	        metrics.put("threads.daemon",threadMXBean.getDaemonThreadCount());

	        // CPU
	        metrics.put("system.cpu.usage",meterRegistry.get("system.cpu.usage").gauge().value());
	        metrics.put("process.cpu.usage",meterRegistry.get("process.cpu.usage").gauge().value());

	        return metrics;
	    }
}
