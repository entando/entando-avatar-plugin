package org.entando.plugin.avatar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class WidgetRequest {

    @JsonProperty("code")
    private String code;

    @JsonProperty("titles")
    private Map<String, String> titles;

    @JsonProperty("group")
    private String group;

    @JsonProperty("customUi")
    private String customUi;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCustomUi() {
        return customUi;
    }

    public void setCustomUi(String customUi) {
        this.customUi = customUi;
    }
}
