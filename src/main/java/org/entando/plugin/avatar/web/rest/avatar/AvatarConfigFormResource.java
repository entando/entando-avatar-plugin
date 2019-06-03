

package org.entando.plugin.avatar.web.rest.avatar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.entando.plugin.avatar.domain.Avatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/form")
public class AvatarConfigFormResource {

    private final Logger log = LoggerFactory.getLogger(AvatarConfigFormResource.class);

    @GetMapping(value = "/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJsonSchemaFormConfiguration() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(Avatar.class);

        return objectMapper.writeValueAsString(jsonSchema);
    }
}
