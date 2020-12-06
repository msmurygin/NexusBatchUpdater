package ru.rencredit.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Getter
@NoArgsConstructor
public class MavenFileResolver implements Resolver<PathResolver> {

    private static final String POM =".pom";
    private static final String JAR =".jar";
    private List<File> filesToProcess = new ArrayList<>();



    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public List<PathResolver> resolve(String path) {
        listf(path);
        return filesToProcess.stream()
                .filter(this::isPOM)
                .map(this::buildPathResolverFIle)
                .filter(distinctByKey(key -> key.getBasePath()))
                .collect(Collectors.toList());
    }

    private boolean isPOM(File file){
       return file.getAbsolutePath().endsWith(POM) || file.getAbsolutePath().endsWith(JAR);
    }

    private PathResolver buildPathResolverFIle(File file){
        String baseFileName = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".") + 1);
        String basePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("/") + 1);

        return PathResolver.builder()
                .basePath(basePath)
                .extension(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1))
                .jarFileName(baseFileName + "jar")
                .pomFileName(baseFileName + "pom")
                .jarFIle(new File(baseFileName + "jar"))
                .pomFile(new File(baseFileName + "pom"))
                .fileName(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("/") + 1, file.getAbsolutePath().lastIndexOf(".")))
                .build();
    }




    private void listf(String directoryName) {
        File directory = new File(directoryName);
        Stream.of(directory.listFiles())
                .forEach(this::processFileDirectories);
    }

    private void processFileDirectories(File file){
        if (file.isFile())
            filesToProcess.add(file);
        else if (file.isDirectory())
            listf(file.getAbsolutePath());
    }
}
