package com.wealthonboard.form.model;

import java.util.List;

public record FormSection(
        String id,
        String title,
        List<FormField> fields
) {
}