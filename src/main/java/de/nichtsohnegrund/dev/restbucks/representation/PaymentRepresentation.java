/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks.representation;

import de.nichtsohnegrund.dev.restbucks.model.Payment;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@XmlRootElement(name="payment", namespace=Representation.RESTBUCKS_NAMESPACE)
public class PaymentRepresentation extends Representation{

    
    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    private double amount;
    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    private String cardholderName;
    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    private String cardNumber;
    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    private int expiryMonth;
    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
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
