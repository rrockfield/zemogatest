package com.rockfield.zemogatest.service;

import com.rockfield.zemogatest.model.Portfolio;
import java.util.List;

/**
 *
 * @author Rockfield
 */
public interface PortfolioService {
    
    List<Portfolio> getAllUsers();
    
    Portfolio getUserInfo(Long twitterId);

    Portfolio modifyUserInfo(Long twitterId, String twitterUserName, String name, String image, String description);
}
