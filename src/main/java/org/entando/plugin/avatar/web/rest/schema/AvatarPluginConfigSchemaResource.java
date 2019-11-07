

package org.entando.plugin.avatar.web.rest.schema;

import org.entando.plugin.avatar.config.AvatarPluginConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/schemas/application")
public class AvatarPluginConfigSchemaResource {

    private final Logger log = LoggerFactory.getLogger(AvatarPluginConfigSchemaResource.class);

    @GetMapping(produces = "application/schema+json")
    public String getJsonSchemaFormConfiguration() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(AvatarPluginConfig.class);

        return objectMapper.writeValueAsString(jsonSchema);
    }
}