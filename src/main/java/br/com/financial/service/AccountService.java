package br.com.financial.service;

import br.com.financial.model.Account;
import br.com.financial.util.StatusEnum;
import br.com.financial.util.AccountTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface AccountService {

    public Page<Account> findAll(Pageable pageable);
    public Page<Account> filterExpirationAndDescriptionAndType(Date expirationDate, String description, AccountTypeEnum type, Pageable pageable);
    public Account findById(Long id);
    public void importAccountsCSV(MultipartFile file);
    public Account create(Account account);
    public Account update(Long id, Account account);
    public void delete(Long id);
    public Account updateStatus(Long id, StatusEnum newStatus);

    public Double findByPaymentDate(Date paymentDateStart, Date paymentDateEnd, AccountTypeEnum type);
}
