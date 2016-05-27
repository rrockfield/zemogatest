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

    @RequestMapping(value = "/zemoga_portfolio_api/user_info", method = RequestMethod.GET)
    public List<Portfolio> getUserInfo() {
        return portfolioService.getAllUsers();
    }

    @RequestMapping(value = "/zemoga_portfolio_api/user_by_twitter", method = RequestMethod.GET)
    public Portfolio getUserByTwitter(@RequestParam(value = "twitter") String twitter) {
        return portfolioService.getUserInfo("GoT_Tyrion");
    }
}
