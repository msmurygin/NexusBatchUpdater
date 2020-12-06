package ru.rencredit.app;

import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class NexusAuthenticator/* implements ClientRequestFilter */{
    private final String user;
    private final String password;

    NexusAuthenticator(@Value("${nexus.userId}")String user, @Value("${nexus.password}")String password) {
        this.user = user;
        this.password = password;
    }
    public HTTPBasicAuthFilter getAuth(){
       return new HTTPBasicAuthFilter(this.user ,  this.password );
    }
}
