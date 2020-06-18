package com.app.service;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.stereotype.Service;


@Service
public class H2DatabaseConnection { 
	  
    public  ProcessEngineConfiguration getConfiguration() 
    { 
        String url = "jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1"; 
        String username = "sa"; 
        String password = ""; 
        String driver="org.h2.Driver";
       
        ProcessEngineConfiguration   cfg = new StandaloneProcessEngineConfiguration()
				      .setJdbcUrl(url)
				      .setJdbcUsername(username)
				      .setJdbcPassword(password)
				      .setJdbcDriver(driver)
				      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

 
        return cfg; 
    } 
} 