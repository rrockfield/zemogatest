package com.rockfield.zemogatest.persistence;

import com.rockfield.zemogatest.model.Portfolio;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rockfield
 */
@Repository
@SuppressWarnings("unchecked")
public class PortfolioDAOImpl implements PortfolioDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Portfolio> getAllUsers() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Portfolio.findAll");
        return query.list();
    }

    @Override
    public Portfolio getUserInfo(String twitterUserName) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Portfolio.findByTwitterUserName");
        query.setParameter("twitterUserName", twitterUserName);
        return (Portfolio) query.uniqueResult();
    }
}
