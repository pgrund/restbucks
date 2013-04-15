package de.nichtsohnegrund.dev.restbucks;

import de.nichtsohnegrund.dev.restbucks.exceptions.InvalidOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.NoSuchOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.OrderDeletionException;
import de.nichtsohnegrund.dev.restbucks.model.Item;
import de.nichtsohnegrund.dev.restbucks.model.Order;
import de.nichtsohnegrund.dev.restbucks.model.OrderStatus;
import java.util.HashMap;
import java.util.Map;

/**
 * Business logic for administration of {@link Order} objects, using
 * map for non-persistent in-memory storage.
 * 
 * 
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class OrderService {

     private static long counterIncr = 2;
    
     private Map<Long, Order> allOrders = new HashMap<Long, Order>();
   
     private void validateOrder(Order order) throws InvalidOrderException {
        if(order == null) {
            System.out.println("null order");
            throw new InvalidOrderException();
        }
        if(order.location == null) {
            System.out.println("null location");
            throw new InvalidOrderException();
        }
        if(order.items == null || order.items.isEmpty()) {
            System.out.println("null items");
            throw new InvalidOrderException(); 
        }
        for (Item i : order.items){
            validateItem(i);
        }
    }
    
    private void validateItem(Item item) throws InvalidOrderException {
        if(item == null){
            System.out.println("null item");
            throw new InvalidOrderException(); 
        }
        if(item.getName() == null){
            System.out.println("null item.name");
            throw new InvalidOrderException(); 
        }
        if(item.getMilk() == null){
            System.out.println("null item.milk");
            throw new InvalidOrderException(); 
        }
        if(item.getQuantity() <=0){
            System.out.println("null item.quantity");
            throw new InvalidOrderException(); 
        }
        if(item.getSize() == null){
            System.out.println("null item.size");
            throw new InvalidOrderException(); 
        }
    }

    public void setAllOrders(Map<Long, Order> allOrders) {
        this.allOrders = allOrders;
    }
    

    /**
     * Validate and add a new {@link Order} to the list of known orders.
     * 
     * @param order {@link Order}
     * @return uniqe identifier as {@link UUID}
     * @throws InvalidOrderException
     */
    public Long createOrder(Order order) throws InvalidOrderException {
        
        validateOrder(order);
        order.status = OrderStatus.UNPAID;

        Long identifier = counterIncr++;
        order.id = identifier;
        allOrders.put(identifier, order);
        
        return identifier;
    }
    
    /**
     * Read a known {@link Order} by its unique id, or throw an exception if unknown.
     * 
     * @param id uniqe identifier
     * @return known {@link Order} or <strong>null</strong>
     * @throws NoSuchOrderException
     */
    public Order readOrder(Long id) throws NoSuchOrderException {
        Order order = allOrders.get(id);
        
        if(order == null) {
            throw new NoSuchOrderException();
        }
        return order;
    }
    
    /**
     * Replace a known {@link Order} known by its uniqe identifier.
     * 
     * @param order new data as {@link Order} for updating
     * @param id unique identifier of existing order
     * @return updated {@link Order}
     * @throws NoSuchOrderException if identifier is not known
     * @throws IllegalStateException if not identifier present
     * @throws InvalidOrderException if passed {@link Order} is not valid
     */
    public Order updateOrder(Order order, Long id) throws NoSuchOrderException,
            IllegalStateException, InvalidOrderException{
        if(id == null) {
            throw new IllegalStateException("no id present");
        }
        
        Order old = allOrders.get(id);
        if(old == null) {
            throw new NoSuchOrderException();
        }
        // changes only for unpaid orders
        if(old.status != OrderStatus.UNPAID) {
            throw new InvalidOrderException();
        }
        
        validateOrder(order);
        allOrders.put(id, order);
        return order;
    }
    
    /**
     * Delete a known {@link Order}, i.e. set its status according to state machine.
     * 
     * @param id uniqe identifier
     * @throws NoSuchOrderException if id unknown
     * @throws OrderDeletionException if state machine does not allow deleting
     */
    public void deleteOrder(Long id) throws OrderDeletionException,
            NoSuchOrderException {
        Order order = readOrder(id);
        
        switch(order.status) {
            case UNPAID:
                order.status = OrderStatus.CANCELED;
                break;
            case READY:
                order.status = OrderStatus.DELIVERED;
                break;
            default:
                throw new OrderDeletionException();
        }
        allOrders.put(id, order);
    }

}
