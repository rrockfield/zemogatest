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
    public Portfolio getUserInfo(@RequestParam(value = "twitter") String twitter) {
        return portfolioService.getUserInfo("twitter");
    }

    @RequestMapping(value = "/zemoga_portfolio_api/modify_user_info", method = RequestMethod.POST)
    public Portfolio modifyUserInfo(@RequestParam(value = "twitter") String twitter,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "description", required = false) String description) {
        return portfolioService.modifyUserInfo(twitter, name, image, description);
    }
}
