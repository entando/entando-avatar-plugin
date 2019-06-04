package org.entando.plugin.avatar.web.rest;


import org.entando.plugin.avatar.AvatarPluginApp;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.entando.plugin.avatar.web.rest.TestUtil.createFormattingConversionService;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvatarPluginApp.class)
public class AvatarWidgetResourceIntTest {

    @Autowired
    private WidgetService avatarWidgetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Qualifier("defaultValidator")
    @Autowired
    private Validator validator;

    private MockMvc restAvatarWidgetResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvatarWidgetResource avatarResource = new AvatarWidgetResource(avatarWidgetService);
        this.restAvatarWidgetResource = MockMvcBuilders.standaloneSetup(avatarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    public void should_be_able_to_store_and_retrieve_widgets() throws Exception {
        try {
            String testWidget = "{" +
                "    \"code\": \"test-widget\"," +
                "    \"titles\": {" +
                "        \"it\": \"Titolo di test\"," +
                "        \"en\": \"Test title\"" +
                "    }," +
                "    \"group\": \"free\"," +
                "    \"customUi\": \"<h1>My fancy Test</h1>\"" +
                "}";

            test_widget_submission(testWidget);
            test_find_widget(testWidget);

        } finally {
            clean_widget_folder();
        }

    }

    private void test_find_widget(String widget) throws Exception {
        restAvatarWidgetResource.perform(get("/api/widgets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].code").value(hasItem("test-widget")))
            .andExpect(jsonPath("$.[*].customUi").value(hasItem("<h1>My fancy Test</h1>")))
            .andExpect(jsonPath("$.[*].titles.en").value(hasItem("Test title")))
            .andExpect(jsonPath("$.[*].titles.it").value(hasItem("Titolo di test")));
    }

    private void test_widget_submission(String testWidget) throws Exception {
        restAvatarWidgetResource.perform(post("/api/widgets")
            .contentType(MediaType.APPLICATION_JSON).content(testWidget))
            .andExpect(status().isCreated());
    }

    private void clean_widget_folder() {
        String widgetFolder = "src/test/resources/widgets";
        try (Stream<Path> walk = Files.walk(Paths.get(widgetFolder))) {
                walk.map(Path::toFile)
                .filter(file -> !file.getPath().equals(widgetFolder))
                .forEach(File::delete);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

}
