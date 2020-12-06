package ru.rencredit.app;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.File;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
@Slf4j
public class NexusFacade implements RemoteApiPost {

    @Autowired
    private CommonConnectionSettings commonConnectionSettings;


    public ClientResponse uploadFile(File file, String groupId, String artifactId, String extension, String version) {

        ClientConfig clientConfig = new DefaultClientConfig();

        Client client = Client.create(clientConfig);
        client.addFilter(commonConnectionSettings.getNexusAuthenticator().getAuth());

        WebResource webResource = client
                .resource(commonConnectionSettings.BASE_URL + commonConnectionSettings.NEXUS_API)
                .queryParam("repository", commonConnectionSettings.getRepository());

        final MultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FileDataBodyPart("maven2.asset1", file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.bodyPart(new FormDataBodyPart("maven2.asset1.extension", extension, MediaType.TEXT_PLAIN_TYPE));
        multiPart.bodyPart(new FormDataBodyPart("maven2.groupId", groupId, MediaType.TEXT_PLAIN_TYPE));
        multiPart.bodyPart(new FormDataBodyPart("maven2.artifactId", artifactId, MediaType.TEXT_PLAIN_TYPE));
        multiPart.bodyPart(new FormDataBodyPart("maven2.version", version, MediaType.TEXT_PLAIN_TYPE));
       // multiPart.bodyPart(new FormDataBodyPart("maven2.generate-pom", "false", MediaType.TEXT_PLAIN_TYPE));
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.MULTIPART_FORM_DATA);

        ClientResponse response = builder.type("multipart/form-data").post(ClientResponse.class, multiPart);

        try {
            if (response.getStatus() != 200) {
                if (response.getLength() != -1 ){
                    log.error("Failed : HTTP error code : " + response.getStatus() + ", " + response.getEntity(String.class));
                }
            } else {
                log.info(response.getLength() + " ");
                log.info("HTTTP OK: 200: /" + response.getStatus() + ", " + response.getEntity(String.class));
            }
        }catch (Exception e){
            log.error("Could not handler response "+ e.getMessage());
        }

        client.destroy();
        return response;
    }

}
