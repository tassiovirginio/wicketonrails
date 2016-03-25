package br.wicketonrails.pages.menu;

import br.wicketonrails.pages.UsersPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by tassio on 25/03/16.
 */
public class MenuPanel extends Panel{

    public MenuPanel(String id) {
        super(id);


        add(new Link("lkUsers") {
            @Override
            public void onClick() {
                setResponsePage(UsersPage.class);
            }
        });


    }
}
