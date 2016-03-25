package br.wicketonrails.pages;

import br.wicketonrails.business.UserBusiness;
import br.wicketonrails.entities.User;
import br.wicketonrails.pages.base.BasePage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.Property;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

@MountPath("users")
//@AuthorizeInstantiation("SIGNED_IN")
//@AuthorizeAction(action = "ENABLE", roles = {"ADMIN"})
//@RequireHttps
public class UsersPage extends BasePage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private UserBusiness userBusiness;

    public UsersPage() {
        this(new User());

    }

    public UsersPage(final User user) {

        CompoundPropertyModel compoundPropertyModel  = new CompoundPropertyModel<User>(user);


        Form<User> form = new Form<User>("form",compoundPropertyModel) {
            @Override
            protected void onSubmit() {
                userBusiness.save(user);
                setResponsePage(UsersPage.class);
            }
        };

        form.add(new TextField<String>("nome").add(vBean()));
        form.add(new EmailTextField("email").add(vBean()));
        add(form);

        List<User> userList = userBusiness.listAll();

        ListView<User> userListView = new ListView<User>("userListView", userList) {
            @Override
            protected void populateItem(ListItem<User> listItem) {
                final User user = listItem.getModelObject();
                listItem.add(new Label("id", user.getId()));
                listItem.add(new Label("name", user.getNome()));
                listItem.add(new Label("email", user.getEmail()));
                listItem.add(new Link("edit") {
                    @Override
                    public void onClick() {
                        setResponsePage(new UsersPage(user));
                    }
                });
                listItem.add(new Link("delete") {
                    @Override
                    public void onClick() {
                        userBusiness.delete(user);
                        setResponsePage(new UsersPage());
                    }
                });
            }
        };

        add(userListView);


    }

}
