package com.wealthonboard.form.model;

import java.util.List;

public record FormTemplate(
        String formId,
        String title,
        List<FormSection> sections
) {
}