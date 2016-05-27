package com.rockfield.zemogatest.controller;

import com.rockfield.zemogatest.model.Portfolio;
import com.rockfield.zemogatest.service.PortfolioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Rockfield
 */
@RestController
public class PortfolioAPI {

    @Autowired
    private PortfolioService portfolioService;

    @RequestMapping(value = "/zemoga_portfolio_api/all_users", method = RequestMethod.GET)
    public List<Portfolio> getAllUsers() {
        return portfolioService.getAllUsers();
    }

    @RequestMapping(value = "/zemoga_portfolio_api/user_info", method = RequestMethod.GET)
    public Portfolio getUserInfo(@RequestParam(value = "id") String twitterId) {
        Long id = Long.parseLong(twitterId);
        return portfolioService.getUserInfo(id);
    }

    @RequestMapping(value = "/zemoga_portfolio_api/modify_user_info", method = RequestMethod.POST)
    public Portfolio modifyUserInfo(@RequestParam(value = "id") String twitterId,
            @RequestParam(value = "twitter") String twitterUserName,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "description", required = false) String description) {
        Long id = Long.parseLong(twitterId);
        return portfolioService.modifyUserInfo(id, twitterUserName, name, image, description);
    }
}
