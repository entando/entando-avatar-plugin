package org.entando.plugin.avatar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.entando.plugin.avatar.domain.WidgetRequest;
import org.entando.plugin.avatar.web.rest.errors.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WidgetService {

    @Value("${widgets.folder:src/main/resources/widgets}")
    private String widgetFolder;

    private final Logger logger = LoggerFactory.getLogger(WidgetService.class);
    private final ResourceLoader widgetResourceLoader;
    private ObjectMapper objectMapper;

    public WidgetService(ResourceLoader widgetResourceLoader) {
        this.widgetResourceLoader = widgetResourceLoader;
        this.objectMapper = new ObjectMapper();
    }

    public List<WidgetRequest> findAll() throws IOException {
        List<File> widgetResources = this.loadResources(widgetFolder);
        return widgetResources.stream().map(file -> {
            try {
                return objectMapper.readValue(file, WidgetRequest.class);
            } catch (IOException e) {
                logger.error("An error occurred while converting resource " + file.getAbsolutePath(), e);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void save(WidgetRequest widgetRequest) {

        File widgetFile = getWidgetFile(widgetRequest.getCode());
        try {
            if (widgetFile.createNewFile()) {
                objectMapper.writeValue(widgetFile, widgetRequest);
            } else {
                logger.error("The widget file " + widgetRequest.getCode() + ".json already exists and override is not supported");
                throw new UnsupportedOperationException("Widget " + widgetRequest.getCode() + " already exists and override is not supported");
            }
        } catch (IOException e) {
            logger.error("An error occurred while saving widget " + widgetRequest.getCode() + " on disk", e);
            throw new RuntimeException(e);
        }
    }

    private List<File> loadResources(String widgetFolder) throws IOException {
//        return ResourcePatternUtils.getResourcePatternResolver(widgetResourceLoader).getResources(pattern);
        return Files.walk(Paths.get(widgetFolder))
            .map(Path::toFile)
            .filter(file -> file.getName().endsWith(".json"))
            .collect(Collectors.toList());
    }

    private File getWidgetFile(String widgetName) {
        createWidgetFolderIfRequired();
        return new File(String.format("%s/%s.json", widgetFolder, widgetName));
    }

    private void createWidgetFolderIfRequired() {
        File directory = new File(widgetFolder);
        if (! directory.exists()){
            // Make the entire directory path including parents,
            directory.mkdirs();
        }
    }

}

