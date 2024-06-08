package br.com.financial.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 2124579555928084094L;

    private String username;
    private Boolean authenticaded;
    private Date created;
    private Date expiration;
    private String acessToken;
    private String refreshToken;

    public TokenDTO() { }

    public TokenDTO(String username,
                   Boolean authenticaded,
                   Date created,
                   Date expiration,
                   String acessToken,
                   String refreshToken) {

        this.username = username;
        this.authenticaded = authenticaded;
        this.created = created;
        this.expiration = expiration;
        this.acessToken = acessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticaded() {
        return authenticaded;
    }

    public void setAuthenticaded(Boolean authenticaded) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenDTO tokenDTO = (TokenDTO) o;
        return Objects.equals(username, tokenDTO.username) && Objects.equals(authenticaded, tokenDTO.authenticaded) && Objects.equals(created, tokenDTO.created) && Objects.equals(expiration, tokenDTO.expiration) && Objects.equals(acessToken, tokenDTO.acessToken) && Objects.equals(refreshToken, tokenDTO.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authenticaded, created, expiration, acessToken, refreshToken);
    }
}
