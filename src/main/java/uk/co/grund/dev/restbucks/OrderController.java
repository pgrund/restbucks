package uk.co.grund.dev.restbucks;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.grund.dev.restbucks.exceptions.NoSuchOrderException;
import uk.co.grund.dev.restbucks.model.Order;
import uk.co.grund.dev.restbucks.model.Payment;
import uk.co.grund.dev.restbucks.model.Receipt;

/**
 * JAX-RS Resource for {@link OrderRepresentation}.
 * 
 * @see OrderActivity
 * @see OrderRepository
 * 
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@RestController
@RequestMapping(value = "/orders")
@ExposesResourceFor(Order.class)
public class OrderController {
   
    private final static Logger LOG = Logger.getLogger(OrderController.class.getName());
    
    private final static String RELATION_BASE = "http://localhost:8080/restbucks.html#relation-";
    
    private final OrderRepository service;
    private final EntityLinks entityLinks;

    
    @Autowired
    public OrderController(OrderRepository service, EntityLinks entityLinks) {
        this.service = service;
        this.entityLinks = entityLinks;
    }
    
    @RequestMapping(
            value = "/{orderId}", 
            method = RequestMethod.GET, 
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public HttpEntity<Resource<Order>> getOrder(@PathVariable("orderId") String orderId)
            throws Exception{
        try {
            LOG.log(Level.INFO, ">>{0}<<", orderId);
            return new ResponseEntity<> (getResource(service.readOrder(Long.parseLong(orderId))), HttpStatus.OK);
        } catch (NoSuchOrderException nsoe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "error for " + orderId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
//    @GET
//    @Path("/{orderId}")
//    @Produces(MediaType.TEXT_HTML)
//    public OrderRepresentation getOrderAsHTML(@PathParam("orderId") String orderId)
//        throws WebApplicationException {
//        try {
//            OrderRepresentation order = service
//                    .read(orderId, uriInfo.getBaseUri());
//            return order;
//        } catch (NoSuchOrderException nsoe) {
//            throw new NotFoundException();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServerErrorException(500);
//        }
//    }
//    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Order> createOrder(Order order) {
        try {
           return getResource(service.createOrder(order));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "invalid order creation:", e);
            return null;
        }
    }
    
    @RequestMapping(
            value = "/{orderId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void removeOrder(@PathVariable("orderId")String orderId) {
        try {
            service.deleteOrder(Long.parseLong(orderId));
            
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "", e);
        }
    }
    
    @RequestMapping(
            value = "/{orderId}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Order> updateOrder(@PathVariable("orderId") String orderId, 
        Order order){
        
        try {
            return getResource(service.updateOrder(order, 
                    Long.parseLong(orderId)));
        } catch (Exception e){
            LOG.log(Level.SEVERE, "{0}", e.getStackTrace());
            return null;
        }
    }
    
    public Resource<Order> getResource(Order order)
        throws URISyntaxException {
        
        // self link always present
        Resource<Order> result = new Resource<>(order, 
                entityLinks.linkForSingleResource(Order.class, order.id)
                        .withSelfRel());
        
               
        switch (order.status) {
            case UNPAID:
                result.add(
                        entityLinks.linkToSingleResource(Order.class, order.id)
                               .withRel(RELATION_BASE + "cancel"),
                        entityLinks.linkToSingleResource(Order.class, order.id)
                               .withRel(RELATION_BASE + "update"),
                        entityLinks.linkToSingleResource(Receipt.class, order.id)
                               .withRel(RELATION_BASE + "pay")
                        );
                break;
            case READY:
               result.add(
                        entityLinks.linkToSingleResource(Receipt.class, order.id)
                               .withRel(RELATION_BASE + "receipt"));
                break;
            case DELIVERED: case CANCELED: case PREPARING: 
                break;
                
        }
        
        return result;
        
    }
}
