package org.entando.plugin.avatar.web.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.entando.plugin.avatar.AvatarPluginApp;
import org.entando.plugin.avatar.domain.request.WidgetRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.entando.keycloak.testutils.WithMockKeycloakUser;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AvatarPluginApp.class)
public class WidgetResourceIntTest {

    private final static ObjectMapper MAPPER = new ObjectMapper();
    private static final String WIDGET_FOLDER = "src/test/resources/widgets";

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc restAvatarWidgetResource;

    @Before
    public void setup() {
        restAvatarWidgetResource = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithMockKeycloakUser(value = "spring", roles = "get-widgets")
    public void should_be_able_to_retrieve_a_widget() throws Exception {
        WidgetRequest testWidget = getRequestFromTestFile();
        ResultActions actions = restAvatarWidgetResource.perform(get("/api/widgets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].code").value(hasItem(testWidget.getCode())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(testWidget.getGroup())))
            .andExpect(jsonPath("$.[*].customUi").value(hasItem(testWidget.getCustomUi())));

        for(String key: testWidget.getTitles().keySet()) {
            actions.andExpect(jsonPath("$.[*].titles." + key).value(hasItem(testWidget.getTitles().get(key))));
        }
    }

    @Test
    public void should_return_unauthorized() throws Exception {
        restAvatarWidgetResource.perform(get("/api/widgets"))
            .andExpect(status().isUnauthorized());
    }

    private void test_find_widget(WidgetRequest widget) throws Exception {


    }

    private void test_unauthorized_widget_submission(WidgetRequest testWidget) throws Exception {
        restAvatarWidgetResource.perform(post("/api/widgets")
            .contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(testWidget)))
            .andExpect(status().isCreated());
    }

//    private void cleanAllWidgetsFiles() {
//        try(Stream<File> widgetFiles = getAllWidgetFiles()) {
//                widgetFiles.forEach(File::delete);
//        } catch (IOException e ) {
//            e.printStackTrace();
//        }
//    }


    private void clearWidgetFiles(String... widgetsFilenames) {
        List<String> filenames = Arrays.stream(widgetsFilenames).map(name -> name.endsWith(".json") ? name : name + ".json").collect(Collectors.toList());
        try(Stream<File> widgetFiles = getAllWidgetFiles()) {
            widgetFiles.filter(file -> filenames.contains(file.getName()))
                .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stream<File> getAllWidgetFiles() throws IOException {
        Stream<Path> walk = Files.walk(Paths.get(WIDGET_FOLDER));
        return walk.map(Path::toFile).filter(file -> !file.getPath().equals(WIDGET_FOLDER));
    }

    private WidgetRequest getRequestFromTestFile() throws IOException {
        return MAPPER.readValue(this.getClass().getResourceAsStream("/widgets/avatar/avatar-metadata.json"), WidgetRequest.class);
    }
}
