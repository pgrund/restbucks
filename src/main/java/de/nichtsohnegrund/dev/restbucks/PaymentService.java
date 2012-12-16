/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks;

import de.nichtsohnegrund.dev.restbucks.exceptions.NoSuchOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.PaymentException;
import de.nichtsohnegrund.dev.restbucks.model.Order;
import de.nichtsohnegrund.dev.restbucks.model.OrderStatus;
import de.nichtsohnegrund.dev.restbucks.model.Payment;
import de.nichtsohnegrund.dev.restbucks.model.Receipt;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class PaymentService {
    
    private OrderService orderService;
    
    private Map<Long, Receipt> allReceipts = new HashMap<Long, Receipt>();

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    
    public void payOrder(Payment payment, Long orderId ) throws NoSuchOrderException,
            PaymentException {
    
        // already payed
        if(allReceipts.containsKey(orderId)){
            throw new PaymentException();
        }
            
        // no order found
        Order order = orderService.readOrder(orderId);
        if(order == null) {
            throw new NoSuchOrderException();
        }
        // wrong status of order
        if (order.getStatus() != OrderStatus.UNPAID) {
            throw new PaymentException();
        }
        // costs and amount do not match
        double costs = order.calculateCosts();
        if( costs != payment.getAmount()) {
            throw new PaymentException();
        }
        
        // create receipt
        Receipt receipt = new Receipt(costs);
        allReceipts.put(orderId, receipt);
        
        // update order
        order.setStatus(OrderStatus.PREPARING);
        
    }
    
    public Receipt getReceiptForOrder(Long orderId) throws PaymentException {
        Receipt result = allReceipts.get(orderId);
        if(result == null) {
            throw new PaymentException();
        }
        
        return result;
    }
}
