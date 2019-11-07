package org.entando.plugin.avatar.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.entando.plugin.avatar.domain.request.WidgetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WidgetService {

    @Value("${entando.widgets-folder}")
    private String widgetFolder;

    private final Logger logger = LoggerFactory.getLogger(WidgetService.class);
    private ObjectMapper objectMapper;

    public WidgetService() {
        this.objectMapper = new ObjectMapper();
    }

    public List<WidgetRequest> findAll() throws IOException {
        List<File> widgetFolders = this.loadWidgetDirectories();
        return widgetFolders.stream().map(this::buildWidgetRequest).filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<File> loadWidgetDirectories() {
        return Arrays
                .asList(Optional.ofNullable(new File(widgetFolder).listFiles(File::isDirectory)).orElse(new File[] {}));
    }

    private Optional<WidgetRequest> buildWidgetRequest(File widgetFolder) {
        WidgetRequest widgetRequest = null;
        Map<String, File> widgetFiles = Arrays
                .stream(Optional.ofNullable(widgetFolder.listFiles(File::isFile)).orElse(new File[] {}))
                .collect(Collectors.toMap(File::getName, f -> f));
        String widgetMetadataFile = widgetFolder.getName() + "-metadata.json";
        String widgetTemplateFile = widgetFolder.getName() + "-template.ftl";
        try {
            if (widgetFiles.containsKey(widgetMetadataFile)) {
                widgetRequest = objectMapper.readValue(widgetFiles.get(widgetMetadataFile), WidgetRequest.class);
                if (widgetFiles.containsKey(widgetTemplateFile)) {
                    String widgetTemplateContent = new String(
                            Files.readAllBytes(Paths.get(widgetFiles.get(widgetTemplateFile).getPath())));
                    widgetRequest.setCustomUi(widgetTemplateContent);
                }
            }
        } catch (IOException e) {
            logger.error("An error occurred while reading widget files from " + widgetFolder.getPath(), e);
        }
        return Optional.ofNullable(widgetRequest);
    }

}
