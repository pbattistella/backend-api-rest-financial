package br.com.financial.repository;

import br.com.financial.model.Account;
import br.com.financial.util.AccountTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public List<Account> findByExpirationDateAndDescriptionContainingAndAccountType(Date expirationDate, String description, AccountTypeEnum type);

    @Query(" SELECT SUM(paymentValue) FROM Account " +
           " WHERE paymentDate >= :paymentDateStart AND paymentDate <= :paymentDateEnd AND accountType = :type")
    public Double findByPaymentDate(@Param("paymentDateStart") Date paymentDateStart,
                                    @Param("paymentDateEnd") Date paymentDateEnd,
                                    @Param("type") AccountTypeEnum type);
}
