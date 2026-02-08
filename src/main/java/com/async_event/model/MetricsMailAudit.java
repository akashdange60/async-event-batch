package com.async_event.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metrics_mail_audit")
public class MetricsMailAudit {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String[] sendTo;

    private String sendFrom;

    private LocalDateTime metricsTime;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] jsonMetrics;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] csvMetrics;

    private String status;
    
 // CSV file
    private String csvFileName;
    private String csvContentType;
    
 // JSON file
    private String jsonFileName;
    private String jsonContentType;

    @Lob
    private String errorMessage;

    private LocalDateTime createdAt = LocalDateTime.now();
	

}
