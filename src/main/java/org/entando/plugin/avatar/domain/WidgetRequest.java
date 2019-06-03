package org.entando.plugin.avatar.domain;

import java.util.Map;

public class WidgetRequest {

    private String code;
    private Map<String, String> titles;
    private String group;
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
