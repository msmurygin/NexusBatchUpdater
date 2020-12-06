package ru.rencredit.app;

import java.io.File;

public interface PomReader {
    Artifact read(File file);
}
