package com.wealthonboard.form.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.JsonNode;

public record VisibilityRule(
        String field,
        @JsonProperty("equals") JsonNode expectedValue
) {
}