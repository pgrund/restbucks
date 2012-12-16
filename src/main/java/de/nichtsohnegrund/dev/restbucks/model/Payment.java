
package de.nichtsohnegrund.dev.restbucks.model;

import java.util.Date;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Payment {

    private final double amount;
    private final String cardholderName;
    private final String cardNumber;
    private final int expiryMonth;
    private final int expiryYear;
    private Date paymentDate;

    public Payment(double amount, String cardholderName, String cardNumber, int expiryMonth, int expiryYear) {
        this.amount = amount;
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.paymentDate = new Date();
    }

    public double getAmount() {
        return amount;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }
    
}

