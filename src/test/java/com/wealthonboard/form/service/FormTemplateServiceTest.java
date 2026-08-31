package com.wealthonboard.form.service;

import com.wealthonboard.form.model.FormField;
import com.wealthonboard.form.model.FormTemplate;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

class FormTemplateServiceTest {

    private final FormTemplateService service =
            new FormTemplateService(JsonMapper.builder().build());

    @Test
    void shouldLoadIndividualInvestorTemplate() {
        FormTemplate template =
                service.loadTemplate("individual-investor");

        assertThat(template.formId())
                .isEqualTo("individual-investor");
        assertThat(template.title())
                .isEqualTo("Individual Investor Onboarding");
        assertThat(template.sections())
                .hasSize(2);

        FormField firstName = findField(template, "firstName");
        assertThat(firstName.type()).isEqualTo("text");
        assertThat(firstName.required()).isTrue();

        FormField province = findField(template, "province");
        assertThat(province.options())
                .containsExactly(
                        "Quebec",
                        "Ontario",
                        "British Columbia",
                        "Alberta"
                );
        assertThat(province.visibleWhen().field())
                .isEqualTo("canadianResident");
        assertThat(province.visibleWhen().expectedValue().booleanValue())
                .isTrue();

        FormField country = findField(template, "country");
        assertThat(country.visibleWhen().expectedValue().booleanValue())
                .isFalse();
    }

    private FormField findField(
            FormTemplate template,
            String fieldId
    ) {
        return template.sections().stream()
                .flatMap(section -> section.fields().stream())
                .filter(field -> field.id().equals(fieldId))
                .findFirst()
                .orElseThrow();
    }
}