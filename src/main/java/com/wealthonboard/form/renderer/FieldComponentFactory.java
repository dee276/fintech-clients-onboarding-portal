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

@org.springframework.stereotype.Component
public class FieldComponentFactory {

    public Component createComponent(FormField field) {
        return switch (field.type()) {
            case "text" -> createTextField(field);
            case "textarea" -> createTextArea(field);
            case "number" -> createNumberField(field);
            case "email" -> createEmailField(field);
            case "date" -> createDatePicker(field);
            case "boolean" -> createBooleanField(field);
            case "select" -> createSelect(field);
            default -> throw new IllegalArgumentException(
                    "Unsupported field type: " + field.type()
            );
        };
    }

    private TextField createTextField(FormField field) {
        TextField component = new TextField(field.label());
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

    private EmailField createEmailField(FormField field) {
        EmailField component = new EmailField(field.label());
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

    private DatePicker createDatePicker(FormField field) {
        DatePicker component = new DatePicker(field.label());
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

    private TextArea createTextArea(FormField field) {
        TextArea component = new TextArea(field.label());
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

    private NumberField createNumberField(FormField field) {
        NumberField component = new NumberField(field.label());
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

    private RadioButtonGroup<Boolean> createBooleanField(
            FormField field
    ) {
        RadioButtonGroup<Boolean> component =
                new RadioButtonGroup<>();

        component.setLabel(field.label());
        component.setItems(true, false);
        component.setItemLabelGenerator(
                value -> value ? "Yes" : "No"
        );
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

    private Select<String> createSelect(FormField field) {
        Select<String> component = new Select<>();
        component.setLabel(field.label());
        component.setItems(field.options());
        component.setId(field.id());
        component.setRequiredIndicatorVisible(field.required());
        return component;
    }

}
