package com.wealthonboard.form.renderer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.wealthonboard.form.model.FormTemplate;
import com.wealthonboard.form.service.FormTemplateService;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicFormRendererTest {

    private final FormTemplateService templateService =
            new FormTemplateService(JsonMapper.builder().build());

    private final DynamicFormRenderer renderer =
            new DynamicFormRenderer(new FieldComponentFactory());

    @Test
    void shouldRenderIndividualInvestorTemplate() {
        FormTemplate template =
                templateService.loadTemplate("individual-investor");

        VerticalLayout form = renderer.render(template);

        assertThat(form.getComponentCount()).isEqualTo(2);

        VerticalLayout personalInformation =
                sectionAt(form, 0, "section-personal-information");

        assertThat(personalInformation.getComponentCount())
                .isEqualTo(5);
        assertField(personalInformation, 1, TextField.class, "firstName");
        assertField(personalInformation, 2, TextField.class, "lastName");
        assertField(personalInformation, 3, DatePicker.class, "dateOfBirth");
        assertField(personalInformation, 4, EmailField.class, "email");

        VerticalLayout residency =
                sectionAt(form, 1, "section-residency");

        assertThat(residency.getComponentCount()).isEqualTo(5);
        assertField(
                residency,
                1,
                RadioButtonGroup.class,
                "canadianResident"
        );
        assertField(residency, 2, Select.class, "province");
        assertField(residency, 3, TextField.class, "country");
        assertField(residency, 4, TextField.class, "foreignTaxId");
    }

    private VerticalLayout sectionAt(
            VerticalLayout form,
            int index,
            String expectedId
    ) {
        Component component = form.getComponentAt(index);
        assertThat(component).isInstanceOf(VerticalLayout.class);
        assertThat(component.getId()).contains(expectedId);
        return (VerticalLayout) component;
    }

    private void assertField(
            VerticalLayout section,
            int index,
            Class<? extends Component> expectedType,
            String expectedId
    ) {
        Component component = section.getComponentAt(index);
        assertThat(component).isInstanceOf(expectedType);
        assertThat(component.getId()).contains(expectedId);
    }
}
