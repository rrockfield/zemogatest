package com.rockfield.zemogatest.service;

import com.rockfield.zemogatest.model.Portfolio;
import com.rockfield.zemogatest.persistence.PortfolioDAO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rockfield
 */
@Transactional
@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioDAO portfolioDAO;

    @Override
    public List<Portfolio> getAllUsers() {
        return portfolioDAO.getAllUsers();
    }

    @Override
    public Portfolio getUserInfo(String twitterUserName) {
        return portfolioDAO.getUserInfo(twitterUserName);
    }

    public Portfolio modifyUserInfo(String twitter, String name, String image, String description) {
        return portfolioDAO.modifyUserInfo(twitter, name, image, description);
    }
}
