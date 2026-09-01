package com.wealthonboard.form.renderer;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.wealthonboard.form.model.FormSection;
import com.wealthonboard.form.model.FormTemplate;
import org.springframework.stereotype.Component;

@Component
public class DynamicFormRenderer {

    private final FieldComponentFactory componentFactory;

    public DynamicFormRenderer(
            FieldComponentFactory componentFactory
    ) {
        this.componentFactory = componentFactory;
    }

    public VerticalLayout render(FormTemplate template) {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.addClassName("dynamic-form");
        formLayout.setPadding(false);
        formLayout.setWidthFull();

        template.sections()
                .stream()
                .map(this::renderSection)
                .forEach(formLayout::add);

        return formLayout;
    }

    private VerticalLayout renderSection(FormSection section) {
        VerticalLayout sectionLayout = new VerticalLayout();
        sectionLayout.setId("section-" + section.id());
        sectionLayout.addClassName("form-section");
        sectionLayout.setPadding(false);
        sectionLayout.setWidthFull();

        H2 title = new H2(section.title());
        title.addClassName("form-section-title");
        sectionLayout.add(title);

        section.fields()
                .stream()
                .map(componentFactory::createComponent)
                .forEach(sectionLayout::add);

        return sectionLayout;
    }
}