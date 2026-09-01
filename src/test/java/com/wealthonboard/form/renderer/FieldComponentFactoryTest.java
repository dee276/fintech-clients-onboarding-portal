package com.wealthonboard.form.renderer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.wealthonboard.form.model.FormField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FieldComponentFactoryTest {

    private final FieldComponentFactory factory =
            new FieldComponentFactory();

    @Test
    void shouldCreateConfiguredTextField() {
        Component component = factory.createComponent(
                field("firstName", "text", "First name", true)
        );

        assertThat(component).isInstanceOf(TextField.class);

        TextField textField = (TextField) component;
        assertThat(textField.getId()).contains("firstName");
        assertThat(textField.getLabel()).isEqualTo("First name");
        assertThat(textField.isRequiredIndicatorVisible()).isTrue();
    }

    @Test
    void shouldCreateEmailField() {
        Component component = factory.createComponent(
                field("email", "email", "Email", true)
        );

        assertThat(component).isInstanceOf(EmailField.class);
    }

    @Test
    void shouldCreateTextArea() {
        Component component = factory.createComponent(
                field("sourceOfFunds", "textarea", "Source of funds", true)
        );

        assertThat(component).isInstanceOf(TextArea.class);
    }

    @Test
    void shouldCreateNumberField() {
        Component component = factory.createComponent(
                field("netWorth", "number", "Approximate net worth", false)
        );

        assertThat(component).isInstanceOf(NumberField.class);
    }

    @Test
    void shouldCreateDatePicker() {
        Component component = factory.createComponent(
                field("dateOfBirth", "date", "Date of birth", true)
        );

        assertThat(component).isInstanceOf(DatePicker.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldCreateBooleanRadioGroup() {
        Component component = factory.createComponent(
                field(
                        "canadianResident",
                        "boolean",
                        "Are you a Canadian resident?",
                        true
                )
        );

        assertThat(component).isInstanceOf(RadioButtonGroup.class);

        RadioButtonGroup<Boolean> radioGroup =
                (RadioButtonGroup<Boolean>) component;

        assertThat(radioGroup.getListDataView().getItems())
                .containsExactly(true, false);
        assertThat(radioGroup.getValue()).isNull();
        assertThat(radioGroup.isRequiredIndicatorVisible()).isTrue();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldCreateSelectWithOptions() {
        FormField province = new FormField(
                "province",
                "select",
                "Province",
                true,
                List.of("Quebec", "Ontario"),
                null
        );

        Component component = factory.createComponent(province);

        assertThat(component).isInstanceOf(Select.class);

        Select<String> select = (Select<String>) component;
        assertThat(select.getListDataView().getItems())
                .containsExactly("Quebec", "Ontario");
    }

    @Test
    void shouldRejectUnsupportedFieldType() {
        FormField field =
                field("accountBalance", "currency", "Balance", false);

        assertThatThrownBy(() -> factory.createComponent(field))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported field type: currency");
    }

    private FormField field(
            String id,
            String type,
            String label,
            boolean required
    ) {
        return new FormField(
                id,
                type,
                label,
                required,
                null,
                null
        );
    }

}
