/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.grund.dev.restbucks;

import uk.co.grund.dev.restbucks.exceptions.NoSuchOrderException;
import uk.co.grund.dev.restbucks.exceptions.PaymentException;
import uk.co.grund.dev.restbucks.model.Order;
import uk.co.grund.dev.restbucks.model.OrderStatus;
import uk.co.grund.dev.restbucks.model.Payment;
import uk.co.grund.dev.restbucks.model.Receipt;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@Repository
public class PaymentService {
    
    private final static Logger LOG = Logger.getLogger(PaymentService.class.getName());
     
    private OrderRepository orderService;
    private final Map<Long, Receipt> allReceipts = new HashMap<>();

    public PaymentService() {
    }

    
    public void setOrderService(OrderRepository orderService) {
        this.orderService = orderService;
    }
    
    public Payment payOrder(Payment payment, Long orderId ) throws NoSuchOrderException,
            PaymentException {
    
        // already payed
        if(allReceipts.containsKey(orderId)){
            throw new PaymentException();
        }
            
        // no order found handled by orderService
        Order order = orderService.readOrder(orderId);
      
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
        
     
        return payment;
    }
    
    public Receipt getReceiptForOrder(Long orderId) throws PaymentException {
        Receipt result = allReceipts.get(orderId);
        if(result == null) {
            throw new PaymentException();
        }
        
        return result;
    }
}
