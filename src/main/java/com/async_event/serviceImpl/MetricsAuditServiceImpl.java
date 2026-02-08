package com.async_event.serviceImpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.async_event.model.MetricsMailAudit;
import com.async_event.repository.MetricsMailAuditRepository;
import com.async_event.service.MetricsAuditService;

@Component
public class MetricsAuditServiceImpl implements MetricsAuditService{
	
	private final MetricsMailAuditRepository repository;

    public MetricsAuditServiceImpl(MetricsMailAuditRepository repository) {
        this.repository = repository;
    }

	@Override
	public void save(MetricsMailAudit audit) {
		// TODO Auto-generated method stub
		 repository.save(audit);
		
	}

}
