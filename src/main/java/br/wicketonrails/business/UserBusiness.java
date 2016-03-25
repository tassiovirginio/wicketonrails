package br.wicketonrails.business;

import br.wicketonrails.business.daos.UserDAO;
import br.wicketonrails.entities.User;
import br.wicketonrails.wicketonrails.BusinessGeneric;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserBusiness extends BusinessGeneric<UserDAO, User> {}
