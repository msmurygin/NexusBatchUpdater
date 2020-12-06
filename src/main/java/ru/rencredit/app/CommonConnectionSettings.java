package ru.rencredit.app;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Getter
public class CommonConnectionSettings {

    @Value("${nexus.url}")
    public String BASE_URL;

    @Value("${nexus.api}")
    public String NEXUS_API;

    @Value("${nexus.default.repo}")
    private String repository;

    @Autowired
    NexusAuthenticator nexusAuthenticator;

}
