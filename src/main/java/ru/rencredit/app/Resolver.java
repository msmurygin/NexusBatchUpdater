package ru.rencredit.app;

import java.io.File;
import java.util.List;

public interface Resolver<T> {
    List<T> resolve(String path);
}
