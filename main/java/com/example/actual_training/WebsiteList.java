package com.example.actual_training;

public class WebsiteList {
    String gameforweb;
    String WebSite;

    public String getGameforweb() {
        return gameforweb;
    }

    public void setGameforweb(String gameforweb) {
        this.gameforweb = gameforweb;
    }

    public String getWebSite() {
        return WebSite;
    }

    public void setWebSite(String webSite) {
        WebSite = webSite;
    }

    public WebsiteList(String gameforweb, String webSite) {
        this.gameforweb = gameforweb;
        WebSite = webSite;
    }
}
