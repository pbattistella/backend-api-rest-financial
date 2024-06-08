package br.com.financial.service;

import br.com.financial.exception.ResourceNotFoundException;
import br.com.financial.model.Account;
import br.com.financial.repository.AccountRepository;
import br.com.financial.util.AccountTypeEnum;
import br.com.financial.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    @Autowired
    private AccountRepository repository;

    @Override
    public Page<Account> findAll(Pageable pageable) {
        logger.info("Finding all accounts.");
        return repository.findAll(pageable);
    }

    @Override
    public List<Account> filterExpirationAndDescriptionAndType(Date expirationDate,
                                                               String description,
                                                               AccountTypeEnum type) {
        logger.info("Filtering accounts.");
        return repository
                .findByExpirationDateAndDescriptionContainingAndAccountType(expirationDate, description, type);
    }

    @Override
    public Account findById(Long id) {
        logger.info("Finding one account.");
        return repository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    @Override
    public Double findByPaymentDate(Date paymentDateStart, Date paymentDateEnd, AccountTypeEnum type) {
        logger.info("Get the amount paid in a period.");
        return repository.findByPaymentDate(paymentDateStart, paymentDateEnd, type);
    }

    @Override
    public Account create(Account account) {
        logger.info("Creating one account.");
        return repository.save(account);
    }

    @Override
    public Account update(Long id, Account account) {
        logger.info("Updating one account.");

        var entity = repository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        entity.setDescription(account.getDescription());
        entity.setAccountType(account.getAccountType());
        entity.setStatus(account.getStatus());
        entity.setExpirationDate(account.getExpirationDate());
        entity.setPaymentDate(account.getPaymentDate());
        entity.setPaymentValue(account.getPaymentValue());

        return repository.save(entity);
    }

    @Override
    public Account updateStatus(Long id, StatusEnum newStatus) {
        logger.info("Updating status of account.");
        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        entity.setStatus(newStatus);

        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting one account.");
        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        repository.delete(entity);
    }
}
