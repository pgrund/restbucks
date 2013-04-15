/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks.representation;

import de.nichtsohnegrund.dev.restbucks.model.Payment;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@XmlRootElement(name="payment", namespace=Representation.RESTBUCKS_NAMESPACE)
@XmlType(name="payment", namespace=Representation.RESTBUCKS_NAMESPACE)
public class PaymentRepresentation extends Representation{

    
    @XmlElement(required = true)
    private double amount;
    @XmlElement(required = true)
    private String cardholderName;
    @XmlElement(required = true)
    private String cardNumber;
    @XmlElement(required = true)
    private int expiryMonth;
    @XmlElement(required = true)
    private int expiryYear;
    
    public PaymentRepresentation() {}

    public PaymentRepresentation(Payment payment, LinkRepresentation... links) {
        this.amount = payment.getAmount();
        this.cardholderName = payment.getCardholderName();
        this.cardNumber = payment.getCardNumber();
        this.expiryMonth = payment.getExpiryMonth();
        this.expiryYear = payment.getExpiryYear();
        this.links = Arrays.asList(links);
    }
    
    @XmlTransient
    public Payment getPayment() {
        Payment result = new Payment(amount, cardholderName, cardNumber, 
                expiryMonth, expiryYear);
        return result;
    }
    
    public LinkRepresentation getOrderLink() {
        return getLinkByName("order");
    }
     public LinkRepresentation getReceiptLink() {
        return getLinkByName("receipt");
    }
}
