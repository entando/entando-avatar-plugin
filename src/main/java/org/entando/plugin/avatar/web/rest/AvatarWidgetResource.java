package org.entando.plugin.avatar.web.rest;

import org.entando.plugin.avatar.domain.WidgetRequest;
import org.entando.plugin.avatar.service.AvatarWidgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AvatarWidgetResource {

    private final Logger log = LoggerFactory.getLogger(AvatarWidgetResource.class);

    private final AvatarWidgetService avatarWidgetService;

    public AvatarWidgetResource(AvatarWidgetService avatarWidgetService) {
        this.avatarWidgetService = avatarWidgetService;
    }

    @GetMapping(value = "/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllWidgets() {
        log.debug("REST request to get all widgets");
        try {
            List<WidgetRequest> widgets = avatarWidgetService.findAll();
            return ResponseEntity.ok(widgets);
        } catch (IOException e) {
            log.error("An error occurred while retrieving widget definitions",e );
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('saveWidget')")
    @PostMapping(value = "/widgets", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity storeWidget(@RequestBody WidgetRequest widgetRequest) {
        log.debug("REST request to store a new widget");
        avatarWidgetService.save(widgetRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
