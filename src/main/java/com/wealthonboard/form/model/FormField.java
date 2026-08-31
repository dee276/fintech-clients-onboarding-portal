package com.wealthonboard.form.model;

import java.util.List;

public record FormField(
        String id,
        String type,
        String label,
        boolean required,
        List<String> options,
        VisibilityRule visibleWhen
) {
}