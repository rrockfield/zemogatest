package com.rockfield.zemogatest.persistence;

import com.rockfield.zemogatest.model.Portfolio;
import java.util.List;

/**
 *
 * @author Rockfield
 */
public interface PortfolioDAO {
    
    List<Portfolio> getAllUsers();
    
    Portfolio getUserInfo(String twitterUserName);

    Portfolio modifyUserInfo(String twitter, String name, String image, String description);
}
