package br.wicketonrails.wicketonrails;

import br.wicketonrails.pages.UsersPage;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import java.util.logging.Logger;

@Component
public class WicketApplication extends WebApplication{//AuthenticatedWebApplication

    static Logger log = Logger.getLogger(WicketApplication.class.getName());

    @Override
    public Class<UsersPage> getHomePage() {
        return UsersPage.class;
    }

    @Override
    public void init() {

        log.info("\n" +
                "********************************************************************\n" +
                "***                          Starting System                     ***\n" +
                "********************************************************************");

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
        new AnnotatedMountScanner().scanPackage(this.getClass().getPackage().getName()+".pages").mount(this);
        new BeanValidationConfiguration().configure(this);


//        getSecuritySettings().setAuthorizationStrategy(new RoleAuthorizationStrategy(this));
//        MetaDataRoleAuthorizationStrategy.authorize(UsersPage.class, Roles.ADMIN);


//        setRootRequestMapper(new HttpsMapper(getRootRequestMapper(),new HttpsConfig(7070, 443)));

    }

//    @Override
//    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
//        return BasicAuthenticationSession.class;
//    }
//
//    @Override
//    protected Class<? extends WebPage> getSignInPageClass() {
//        return SignInPage.class;
//    }

}
