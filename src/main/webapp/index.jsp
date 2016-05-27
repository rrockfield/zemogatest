<%-- 
    Document   : index
    Created on : May 26, 2016, 10:49:13 PM
    Author     : Rockfield
--%>
<%@page import="com.rockfield.zemogatest.model.Portfolio"%>
<%@page import="org.springframework.web.client.RestTemplate"%>
<%@page import="twitter4j.auth.*"%>
<%@page import="twitter4j.*"%>
<%@page import="java.io.IOException"%>
<%!
    private static final String CLIENTID = "DufNQZFymm1h75HDA05bj6zhN";
    private static final String CLIENTSECRET = "3zURYiSYmA4okUmiC0AxmP87G7DY9hTsTFWDglu5T4iZxvklr6";
    RestTemplate restTemplate = new RestTemplate();
    Twitter twitter = null;

    public static String buildServerURL(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(request.getServerName());
        if (request.getServerPort() != 80) {
            sb.append(":").append(request.getServerPort());
        }
        return sb.toString();
    }

    private String handleTwitterAuthenticationRequest(HttpServletRequest request, HttpSession session) throws IOException, TwitterException {
        String serverURL = buildServerURL(request);
        if (twitter == null) {
            twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(CLIENTID, CLIENTSECRET);
        }
        RequestToken requestToken = twitter.getOAuthRequestToken(serverURL + "/ZemogaTest/");
        session.setAttribute("requestToken", requestToken);
        session.setAttribute("twitter", twitter);
        return requestToken.getAuthorizationURL();
    }

    private User handleTwitterAuthenticationResponse(Object objToken, HttpSession session, HttpServletRequest request) throws TwitterException, IOException, JSONException {
        RequestToken requestToken = (RequestToken) objToken;
        session.removeAttribute("requestToken");
        String oauth_verifier = request.getParameter("oauth_verifier");
        AccessToken accTok = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
        session.setAttribute("token", accTok);
        User twitterUser = twitter.verifyCredentials();
        session.setAttribute("user", twitterUser);
        return twitterUser;
    }

%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<%
    Object userObj = session.getAttribute("user");
    User twitterUser;
    if (userObj == null) {
        Object tokenObj = session.getAttribute("requestToken");
        if (tokenObj == null) {
            String authenticationPage = handleTwitterAuthenticationRequest(request, session);
            response.sendRedirect(authenticationPage);
            return;
        } else {
            twitterUser = handleTwitterAuthenticationResponse(tokenObj, session, request);
            response.sendRedirect(buildServerURL(request) + "/ZemogaTest/");
            return;
        }
    } else {
        twitterUser = (User) userObj;
    }

    Portfolio portfolio = restTemplate.getForObject(buildServerURL(request) + "/ZemogaTest/zemoga_portfolio_api/user_info?id=" + twitterUser.getId(), Portfolio.class);
    if (portfolio == null) {
        portfolio = new Portfolio();
        portfolio.setIdportfolio(twitterUser.getId());
        portfolio.setTitle(twitterUser.getName());
        portfolio.setTwitterUserName(twitterUser.getScreenName());
        portfolio.setImageURL(twitterUser.getBiggerProfileImageURL());
        portfolio.setDescription(twitterUser.getDescription());
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Zemoga Twitter</title>
    </head>
    <body>
        <form action="<%=buildServerURL(request)%>/ZemogaTest/zemoga_portfolio_api/modify_user_info" method="POST" target="blank">
            <table width="80%">
                <tr>
                    <td>Twitter ID</td>
                    
                    <td><%=portfolio.getIdportfolio()%><input type="hidden" name="id" value="<%=portfolio.getIdportfolio()%>" /></td>
                </tr>
                <tr>
                    <td><img src="<%=portfolio.getImageURL()%>" /></td>
                    <td><input type="text" size="100" name="image" value="<%=portfolio.getImageURL()%>" /></td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" size="100" value="<%=portfolio.getTitle()%>" name="name" /></td>
                </tr>
                <tr>
                    <td>Screen Name:</td>
                    <td><input type="text" size="45" value="<%=portfolio.getTwitterUserName()%>" name="twitter" /></td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td><input type="text" size="100" value="<%=portfolio.getDescription()%>" name="description" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Update" /></td>
                </tr>
            </table>
        </form>
        <% 
            int i = 0;
            for (Status status : twitter.getUserTimeline(portfolio.getIdportfolio())) { 
        %>
                <li><%= status.getText() %></li>
        <% 
                i++;
                if (i >= 5) {
                    break;
                }
            }
        %>
    </body>
</html>
