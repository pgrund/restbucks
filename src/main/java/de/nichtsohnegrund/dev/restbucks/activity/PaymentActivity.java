/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks.activity;

import de.nichtsohnegrund.dev.restbucks.PaymentService;
import de.nichtsohnegrund.dev.restbucks.representation.LinkRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.PaymentRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.ReceiptRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.Representation;
import java.net.URI;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class PaymentActivity {
    
    private PaymentService service;

    public void setService(PaymentService service) {
        this.service = service;
    }
    

    public PaymentRepresentation payOrder(PaymentRepresentation payment, URI baseUri, String orderId)
            throws Exception {
        LinkRepresentation[] links = new LinkRepresentation[] {
          new LinkRepresentation(Representation.RELATIONS_URI + "order", 
                    new URI(baseUri + "order/" + orderId), Representation.RESTBUCKS_MEDIATYPE),
          new LinkRepresentation(Representation.RELATIONS_URI + "receipt", 
                    new URI(baseUri + "receipt/" + orderId), Representation.RESTBUCKS_MEDIATYPE)
                  };
     
        service.payOrder(payment.getPayment(), Long.parseLong(orderId));
        
        return new PaymentRepresentation(payment.getPayment(), links);
    }
    
    public ReceiptRepresentation getReceipt(URI baseUri, String orderId) throws Exception{
        LinkRepresentation[] links = new LinkRepresentation[] {
             new LinkRepresentation(Representation.RELATIONS_URI + "order", 
                    new URI(baseUri + "order/" + orderId), Representation.RESTBUCKS_MEDIATYPE),
        };
        
        return new ReceiptRepresentation (service.getReceiptForOrder(Long.parseLong(orderId)), links);
    }
}
