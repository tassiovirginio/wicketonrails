package br.wicketonrails.pages.login;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by tassio on 25/03/16.
 */
public class BasicAuthenticationSession extends AuthenticatedWebSession {
    private String username;

    public BasicAuthenticationSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(String username, String password) {
        //user is authenticated if username and password are equal
        boolean authResult = username.equals(password);

        if(authResult)
            this.username = username;

        return authResult;
    }

    public Roles getRoles() {
        Roles resultRoles = new Roles();
        //if user is signed in add the relative role
        if(isSignedIn())
            resultRoles.add("SIGNED_IN");
        //if username is equal to 'superuser' add the ADMIN role
        if(username!= null && username.equals("superuser"))
            resultRoles.add(Roles.ADMIN);

        return resultRoles;
    }

    @Override
    public void signOut() {
        super.signOut();
        username = null;
    }
}
