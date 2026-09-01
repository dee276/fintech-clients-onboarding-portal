package com.wealthonboard.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.wealthonboard.form.model.FormTemplate;
import com.wealthonboard.form.renderer.DynamicFormRenderer;
import com.wealthonboard.form.service.FormTemplateService;

@Route("")
@PageTitle("WealthOnboard")
public class MainView extends VerticalLayout {

    private static final String TEMPLATE_ID =
            "individual-investor";

    public MainView(
            FormTemplateService templateService,
            DynamicFormRenderer formRenderer
    ) {
        FormTemplate template =
                templateService.loadTemplate(TEMPLATE_ID);

        H1 title = new H1(template.title());
        title.addClassName("onboarding-title");

        Paragraph description = new Paragraph(
                "Complete the information required "
                        + "to open your onboarding case."
        );
        description.addClassName("onboarding-description");

        add(title, description, formRenderer.render(template));

        addClassName("onboarding-view");
        setWidthFull();
        setMaxWidth("720px");
        getStyle().set("margin", "0 auto");
    }
}
