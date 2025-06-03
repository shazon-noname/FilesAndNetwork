package org.FilesAndNetwork.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
    public List<File> findFiles(String path, String... extensions) {
        List<File> foundFiles = new ArrayList<>();
        File directory = new File(path);

        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory does not exist: " + path);
        }

        searchFiles(directory, foundFiles, extensions);
        return foundFiles;
    }

    private void searchFiles(File directory, List<File> foundFiles, String... extensions) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchFiles(file, foundFiles, extensions);
            } else {
                for (String ext : extensions) {
                    if (file.getName().toLowerCase().endsWith(ext)) {
                        foundFiles.add(file);
                        break;
                    }
                }
            }
        }
    }
}