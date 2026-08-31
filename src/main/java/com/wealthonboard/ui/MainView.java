package com.wealthonboard.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("WealthOnboard")
public class MainView extends VerticalLayout {
    public MainView() {
        add(
            new H1("WealthOnboard"),
            new Paragraph("Fintech client onboarding portal")
        );
        setMaxWidth("960px");
        getStyle().set("margin","0 auto");
    }
}
