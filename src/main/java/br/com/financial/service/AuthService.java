package br.com.financial.service;

import br.com.financial.dto.UserCredentialsDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity signin(UserCredentialsDTO data);
    public ResponseEntity refreshToken(String username, String refreshToken);
}
