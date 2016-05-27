<%-- 
    Document   : index
    Created on : May 26, 2016, 10:49:13 PM
    Author     : Rockfield
--%>
<%@page import="twitter4j.auth.*"%>
<%@page import="twitter4j.*"%>
<%@page import="java.io.IOException"%>
<%!
    private static final String CLIENTID = "DufNQZFymm1h75HDA05bj6zhN";
    private static final String CLIENTSECRET = "3zURYiSYmA4okUmiC0AxmP87G7DY9hTsTFWDglu5T4iZxvklr6";
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
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Zemoga Twitter</title>
    </head>
    <body>
        <form action="<%=buildServerURL(request)%>/zemoga_portfolio_api/modify_user_info" method="POST" target="blank">
            <p><img src="<%=twitterUser.getBiggerProfileImageURL()%>" /><input type="text" name="image" value="<%=twitterUser.getBiggerProfileImageURL()%>" /></p>
            <p>Twitter ID <%=twitterUser.getId()%> <input type="hidden" name="id" value="<%=twitterUser.getId()%>" /></p>
            <p>Name: <input type="text" value="<%=twitterUser.getName()%>" name="name" /></p>
            <p>Screen Name: <input type="text" value="<%=twitterUser.getScreenName()%>" name="twitter" /></p>
            <p>Description: <input type="text" value="<%=twitterUser.getDescription()%>" name="description" /></p>
            <p><input type="submit" value="Change" /></p>
        </form>
        <% 
            int i = 0;
            for (Status status : twitter.getUserTimeline(twitterUser.getId())) { 
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
