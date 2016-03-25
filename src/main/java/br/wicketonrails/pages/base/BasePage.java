package br.wicketonrails.pages.base;

import br.wicketonrails.pages.menu.MenuPanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.https.RequireHttps;

/**
 * Created by tassio on 13/01/15.
 */
public class BasePage extends WebPage {

    public BasePage() {

        add(new FeedbackPanel("feedback"));

        add(new MenuPanel("menu"));
    }

    protected PropertyValidator vBean(){
        return new PropertyValidator();
    }

}
