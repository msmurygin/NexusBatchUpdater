package ru.rencredit.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NexusBatchUpdateService implements  BatchUpdater {

    @Autowired
    private Resolver<PathResolver> pathResolver;

    @Autowired
    private PomReader pomReader;

    @Autowired
    private NexusFacade nexusFacade;


    @Override
    public void update() {
        List<PathResolver> paths = pathResolver.resolve("/home/maxim/.m2/repository/");
        paths.parallelStream().forEach(this::nexusUpdateTask);

    }

    private void nexusUpdateTask(PathResolver mavenFile){
        Artifact artifact = pomReader.read(mavenFile.getPomFile());
        log.info(artifact.toString());


        if (mavenFile.getJarFIle().exists()){
            nexusFacade.uploadFile(mavenFile.getJarFIle(), artifact.getGroupId(), artifact.getArtifactId(), mavenFile.getExtension(), artifact.getVersion());
        }
        if (mavenFile.getPomFile().exists()){
            nexusFacade.uploadFile(mavenFile.getPomFile(), artifact.getGroupId(), artifact.getArtifactId(), "pom", artifact.getVersion());
        }

    }
}
