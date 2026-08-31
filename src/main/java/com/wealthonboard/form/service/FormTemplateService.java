package com.wealthonboard.form.service;

import com.wealthonboard.form.model.FormTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FormTemplateService {

    private final ObjectMapper objectMapper;

    public FormTemplateService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public FormTemplate loadTemplate(String templateId) {
        String resourcePath = "forms/" + templateId + ".json";
        ClassPathResource resource = new ClassPathResource(resourcePath);

        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, FormTemplate.class);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load form template: " + templateId,
                    exception
            );
        }
    }
}