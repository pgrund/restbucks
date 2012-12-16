/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks.representation;

import de.nichtsohnegrund.dev.restbucks.model.Receipt;
import java.util.Arrays;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@XmlRootElement(name="receipt", namespace=Representation.RESTBUCKS_NAMESPACE)
public class ReceiptRepresentation extends Representation {

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    private Double amount;
    @XmlSchemaType(name="date", namespace=Representation.RESTBUCKS_NAMESPACE)
    private Date paid ;
    
    public ReceiptRepresentation() {}


    public ReceiptRepresentation(Receipt receipt, LinkRepresentation... links) {
        this.amount = receipt.getAmount();
        this.paid = receipt.getPaid();
        this.links = Arrays.asList(links);
    }

    
    
    public LinkRepresentation getOrder() {
        return getLinkByName("order");
    }
}
