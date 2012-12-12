
package de.nichtsohnegrund.dev.restbucks.activity;

import de.nichtsohnegrund.dev.restbucks.OrderService;
import de.nichtsohnegrund.dev.restbucks.exceptions.OrderDeletionException;
import de.nichtsohnegrund.dev.restbucks.model.Location;
import de.nichtsohnegrund.dev.restbucks.model.Order;
import de.nichtsohnegrund.dev.restbucks.model.OrderStatus;
import de.nichtsohnegrund.dev.restbucks.representation.LinkRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.OrderRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.Representation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Link;

/**
 * Provide Domain Application Model (HATEOAS) and mapping between
 * {@link Order} and {@link OrderRepresentation}
 * 
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class OrderActivity {
    
    private OrderService service;
    
    private LinkRepresentation[] getHypermedia(String identifier, URI base, OrderStatus status)
        throws URISyntaxException {
        List<LinkRepresentation> links = new ArrayList<LinkRepresentation>();
        
        URI orderUri = new URI(base + "order/"
                + identifier.toString());
        
        switch (status) {
            case UNPAID:
                links.add(new LinkRepresentation(Representation.RELATIONS_URI
                        + "cancel", orderUri));
                links.add(new LinkRepresentation(Representation.RELATIONS_URI
                            + "payment", new URI(base + "payment/"
                        + identifier.toString())));
                links.add(new LinkRepresentation(Representation.RELATIONS_URI
                            + "update", orderUri));
                links.add(new LinkRepresentation(Representation.SELF_REL_VALUE,
                        orderUri));
                break;
            case PREPARING: 
                links.add(new LinkRepresentation(Representation.SELF_REL_VALUE,
                        orderUri));
                break;
            case READY:
                links.add(new LinkRepresentation(Representation.RELATIONS_URI
                            + "receipt", new URI(base + "receipt/"
                        + identifier.toString())));
                links.add(new LinkRepresentation(Representation.SELF_REL_VALUE,
                        orderUri));
                break;
            case DELIVERED: case CANCELED:
                break;
                
        }
        
        return links.toArray(new LinkRepresentation[0]);
        
    }

    public void setService(OrderService service) {
        this.service = service;
    }
    
    
    /* **********************************************
     * 
     *
     ************************************************ */
    
    
    public OrderRepresentation create(OrderRepresentation representation,
            URI baseUri) throws Exception {

        
        Order order = new Order();
        order.status = OrderStatus.UNPAID;
        
        order.items = representation.getItems();
        order.location = Location.valueOf(representation.getLocation());
       
        UUID id = service.createOrder(order);
        
        return new OrderRepresentation(order, getHypermedia(id.toString(),
                baseUri, order.status));
    }

    public OrderRepresentation read(UUID identifier, URI baseUri)
        throws Exception {
        
        Order order = service.readOrder(identifier);
        
        return new OrderRepresentation(order, getHypermedia(identifier.toString(),
                baseUri, order.status));
    }
   

    public OrderRepresentation update(OrderRepresentation representation,
            String identifier, URI baseUri) throws Exception {
        Order order = new Order();
        order.status = OrderStatus.valueOf(representation.getStatus());
        order.items = representation.getItems();
        order.location = Location.valueOf(representation.getLocation());
        order.costs = representation.getCost();
        
        Order result = service.updateOrder(order, UUID.fromString(identifier));
        return new OrderRepresentation(result, getHypermedia(identifier,
                baseUri, result.status));
    }
    
    
    public OrderRepresentation delete(String identifier, URI baseUri)
            throws Exception {
        
        Order order = service.readOrder(UUID.fromString(identifier));
        
        switch (order.status) {
            case UNPAID:
                order.status = OrderStatus.CANCELED;
                break;
            case READY:
                order.status = OrderStatus.DELIVERED;
                break;
            default:
                throw new OrderDeletionException();
        }
        
        return new OrderRepresentation(order, getHypermedia(identifier, baseUri,
                order.status));
        
    }
}
