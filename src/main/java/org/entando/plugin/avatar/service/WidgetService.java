package org.entando.plugin.avatar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.entando.entando.web.widget.model.WidgetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WidgetService {

    private final Logger logger = LoggerFactory.getLogger(WidgetService.class);
    private final ResourceLoader widgetResourceLoader;
    private ObjectMapper objectMapper;

    public WidgetService(ResourceLoader widgetResourceLoader) {
        this.widgetResourceLoader = widgetResourceLoader;
        this.objectMapper = new ObjectMapper();
    }

    public List<WidgetRequest> findAll() throws IOException {
        Resource[] widgetResources = this.loadResources("classpath:/widgets/*.json");
        return Arrays.stream(widgetResources).map(resource -> {
            try {
                return objectMapper.readValue(resource.getFile(), WidgetRequest.class);
            } catch (IOException e) {
                logger.error("An error occurred while converting resource " + resource.getFilename(), e);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    private Resource[] loadResources(String pattern) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(widgetResourceLoader).getResources(pattern);
    }


}
