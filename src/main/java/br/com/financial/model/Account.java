package br.com.financial.model;

import br.com.financial.util.AccountTypeEnum;
import br.com.financial.util.StatusEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1904407144326855519L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountTypeEnum accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusEnum status;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private double paymentValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(double paymentValue) {
        this.paymentValue = paymentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.paymentValue, paymentValue) == 0 && Objects.equals(id, account.id) && Objects.equals(description, account.description) && accountType == account.accountType && status == account.status && Objects.equals(expirationDate, account.expirationDate) && Objects.equals(paymentDate, account.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, accountType, status, expirationDate, paymentDate, paymentValue);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", accountType=" + accountType +
                ", status=" + status +
                ", expirationDate=" + expirationDate +
                ", paymentDate=" + paymentDate +
                ", paymentValue=" + paymentValue +
                '}';
    }
}
