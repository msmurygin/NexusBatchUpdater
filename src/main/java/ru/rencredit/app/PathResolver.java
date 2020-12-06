package ru.rencredit.app;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PACKAGE)
public class PathResolver {
    private String basePath;
    private String fileName;
    private String jarFileName;
    private String pomFileName;
    private File jarFIle;
    private File pomFile;
    private String extension;

}
