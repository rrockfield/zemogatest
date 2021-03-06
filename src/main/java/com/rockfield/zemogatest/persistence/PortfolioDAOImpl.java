package com.rockfield.zemogatest.persistence;

import com.rockfield.zemogatest.model.Portfolio;
import java.util.List;
import org.hibernate.*;
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
    public Portfolio getUserInfo(Long twitterId) {
        return (Portfolio) sessionFactory.getCurrentSession().get(Portfolio.class, twitterId);
    }

    @Override
    public Portfolio modifyUserInfo(Long twitterId, String twitterUserName, String name, String image, String description) {
        Portfolio portfolio;
        portfolio = getUserInfo(twitterId);
        if (portfolio == null) {
            portfolio = new Portfolio();
            portfolio.setIdportfolio(twitterId);
        }
        portfolio.setTwitterUserName(twitterUserName);
        portfolio.setDescription(description);
        portfolio.setImageURL(image);
        portfolio.setTitle(name);
        sessionFactory.getCurrentSession().saveOrUpdate(portfolio);
        return portfolio;
    }
}
