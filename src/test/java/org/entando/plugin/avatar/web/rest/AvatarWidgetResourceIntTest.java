package org.entando.plugin.avatar.web.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.entando.plugin.avatar.AvatarPluginApp;
import org.entando.plugin.avatar.domain.WidgetRequest;
import org.entando.plugin.avatar.service.WidgetService;
import org.entando.plugin.avatar.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
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

import static org.entando.plugin.avatar.web.rest.TestUtil.createFormattingConversionService;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"default", "security"})
public class AvatarWidgetResourceIntTest {

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
    @WithMockUser(value = "spring")
    public void should_be_able_to_retrieve_a_widget() throws Exception {
        WidgetRequest testWidget = getRequestFromTestFile();
        test_find_widget(testWidget);
    }

    @Test
    @WithMockUser(value = "spring", authorities = "saveWidget")
    public void should_submit_file_and_retrieve() throws Exception {
        WidgetRequest testWidget = new WidgetRequest();
        Map<String, String> titles = new HashMap<>();
        titles.put("en", "test-widget");

        testWidget.setCode("test-widget");
        testWidget.setTitles(titles);
        testWidget.setGroup("free");
        testWidget.setCustomUi("<h1>Test Widget</h1>");

        try {
            test_unauthorized_widget_submission(testWidget);
            test_find_widget(testWidget);
        } finally {
            clearWidgetFiles(testWidget.getCode());
        }

    }

    private void test_find_widget(WidgetRequest widget) throws Exception {
       ResultActions actions = restAvatarWidgetResource.perform(get("/api/widgets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].code").value(hasItem(widget.getCode())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(widget.getGroup())))
            .andExpect(jsonPath("$.[*].customUi").value(hasItem(widget.getCustomUi())));

       for(String key: widget.getTitles().keySet()) {
           actions.andExpect(jsonPath("$.[*].titles." + key).value(hasItem(widget.getTitles().get(key))));
       }


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
        return MAPPER.readValue(this.getClass().getResourceAsStream("/widgets/avatar-widget.json"), WidgetRequest.class);
    }
}
