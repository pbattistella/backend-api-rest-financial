package br.com.financial.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "billing")
public class Billing implements Serializable {

    private static final long serialVersionUID = 1904407144326855519L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false )
    private String description;

    @Column( nullable = false, length = 50)
    private String situation;

    @Column( nullable = false )
    private Date expirationDate;

    @Column( nullable = false )
    private Date paymentDate;

    @Column( nullable = false )
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

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
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
        Billing billing = (Billing) o;
        return Double.compare(billing.paymentValue, paymentValue) == 0 && Objects.equals(id, billing.id) && Objects.equals(description, billing.description) && Objects.equals(situation, billing.situation) && Objects.equals(expirationDate, billing.expirationDate) && Objects.equals(paymentDate, billing.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, situation, expirationDate, paymentDate, paymentValue);
    }
}
