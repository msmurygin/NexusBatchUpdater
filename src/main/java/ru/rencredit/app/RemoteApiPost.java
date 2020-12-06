package ru.rencredit.app;

import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.Response;
import java.io.File;

public interface RemoteApiPost {
    ClientResponse uploadFile(File file, String groupId, String artifactId, String extension, String version);
}
