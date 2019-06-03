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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import static org.entando.plugin.avatar.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void should_return_ok() throws Exception {
        restAvatarWidgetResource.perform(get("/api/widgets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[0].code").value("avatar_hello_world"))
            .andExpect(jsonPath("$.[0].customUi").value("<h1>Hello World!</h1>"))
            .andExpect(jsonPath("$.[0].titles.en").value("Hello world"))
            .andExpect(jsonPath("$.[0].titles.it").value("Ciao mondo"));

    }

}
