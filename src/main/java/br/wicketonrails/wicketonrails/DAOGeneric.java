package br.wicketonrails.wicketonrails;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class DAOGeneric<T extends EntityGeneric> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> persistentClass;

    public DAOGeneric() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public org.hibernate.Session session() {
        return sessionFactory.getCurrentSession();
    }

    public Class<T> clazz() {
        return this.persistentClass;
    }

    public void delete(T entity) {
        session().delete(entity);
    }

    public void delete(Long id) {
        String hql = "delete " + clazz().getSimpleName() + " where id = :id";
        Query q = session().createQuery(hql).setParameter("id", id);
        q.executeUpdate();
    }

    public T findById(Long id) {
        return (T) session().get(clazz(), id);
    }

    public List<T> listAll() {
        return findByHQL("FROM " + clazz().getSimpleName());
    }

    public int size() {
        Criteria crit = session().createCriteria(clazz());
        crit.setProjection(Projections.rowCount());
        Object result = crit.uniqueResult();
        Long size = (Long) result;
        return size.intValue();
    }

    public T save(T entity) {
        session().saveOrUpdate(entity);
        return entity;
    }

    public List<T> findByCriteriaReturnList(Criterion... criterion) {
        return findByCriteria(null, criterion);
    }

    public T findByCriteriaReturnUniqueResult(Criterion... criterion) {
        return findByCriteriaReturnUniqueResult(null, criterion);
    }

    public List<T> findByCriteria(Order order, Criterion... criterion) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (order != null) {
            crit.addOrder(order);
        }
        return crit.list();
    }

    public List<T> findByCriteria2(List<Order> orders, List<Criterion> criterion, Map<String, String> alias) {
        Criteria crit = session().createCriteria(clazz());

        if (alias != null) {
            for (Map.Entry<String, String> entry : alias.entrySet()) {
                crit.createAlias(entry.getKey(), entry.getValue());
            }
        }

        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        for (Order o : orders) {
            crit.addOrder(o);
        }
        return crit.list();
    }

    public T findByCriteriaReturnUniqueResult(Order order, Criterion... criterion) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (order != null) {
            crit.addOrder(order);
        }
        return (T) crit.uniqueResult();
    }

    public T findByCriteriaReturnUniqueResult(Order order, int offSet, int size, Criterion... criterion) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.setFirstResult(offSet);
        crit.setMaxResults(size);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (order != null) {
            crit.addOrder(order);
        }
        return (T) crit.uniqueResult();
    }

    public List<T> findByCriteria(Order order) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (order != null) {
            crit.addOrder(order);
        }
        return crit.list();
    }

    public List<T> findByCriteria(Order order, int offSet, int size) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.setFirstResult(offSet);
        crit.setMaxResults(size);
        if (order != null) {
            crit.addOrder(order);
        }
        return crit.list();
    }

    public List<T> findByHQL(Order order, int offSet, int size) {
        Query query = session().createQuery("FROM " + clazz().getSimpleName() + " order by " + order.getPropertyName());
        query.setFirstResult(offSet);
        query.setMaxResults(size);
        return query.list();
    }

    public List<T> findByHQL(String hql) {
        Query query = session().createQuery(hql);
        return query.list();
    }

    public T findByHQLUniqueResult(String hql) {
        Query query = session().createQuery(hql);
        return (T) query.uniqueResult();
    }

}
