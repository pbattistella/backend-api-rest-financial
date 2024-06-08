package br.com.financial.service;

import br.com.financial.model.Account;
import br.com.financial.util.AccountTypeEnum;
import br.com.financial.util.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface AccountService {

    public Page<Account> findAll(Pageable pageable);
    public List<Account> filterExpirationAndDescriptionAndType(Date expirationDate, String description, AccountTypeEnum type);
    public Account findById(Long id);
    public Account create(Account account);
    public Account update(Long id, Account account);
    public void delete(Long id);
    public Account updateStatus(Long id, StatusEnum newStatus);
}
