package br.com.financial.integration_test.dto;

import java.util.Date;

public class UserAuthDTOTest {

    private String username;
    private String authenticaded;
    private Date created;
    private Date expiration;
    private String acessToken;
    private String refreshToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthenticaded() {
        return authenticaded;
    }

    public void setAuthenticaded(String authenticaded) {
        this.authenticaded = authenticaded;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
