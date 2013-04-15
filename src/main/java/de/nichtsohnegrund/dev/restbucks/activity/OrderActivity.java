
package de.nichtsohnegrund.dev.restbucks.activity;

import de.nichtsohnegrund.dev.restbucks.OrderService;
import de.nichtsohnegrund.dev.restbucks.exceptions.OrderDeletionException;
import de.nichtsohnegrund.dev.restbucks.model.Item;
import de.nichtsohnegrund.dev.restbucks.model.Location;
import de.nichtsohnegrund.dev.restbucks.model.Order;
import de.nichtsohnegrund.dev.restbucks.model.OrderStatus;
import de.nichtsohnegrund.dev.restbucks.representation.ItemRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.LinkRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.OrderRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.Representation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
        
        String relationBase = "http://localhost:8080/restbucks.html#relation-";///Representation.RELATIONS_URI;
        switch (status) {
            case UNPAID:
                links.add(new LinkRepresentation(relationBase
                        + "cancel", orderUri, "cancel", Representation.RESTBUCKS_MEDIATYPE));
                links.add(new LinkRepresentation(relationBase
                            + "payment", new URI(base + "payment/"
                        + identifier.toString()), "pay", Representation.RESTBUCKS_MEDIATYPE));
                links.add(new LinkRepresentation(relationBase
                            + "update", orderUri, "update", Representation.RESTBUCKS_MEDIATYPE));
                links.add(new LinkRepresentation(relationBase + Representation.SELF_REL_VALUE,
                        orderUri, "self", Representation.RESTBUCKS_MEDIATYPE));
                break;
            case PREPARING: 
                links.add(new LinkRepresentation(Representation.SELF_REL_VALUE,
                        orderUri, "self", Representation.RESTBUCKS_MEDIATYPE));
                break;
            case READY:
                links.add(new LinkRepresentation(relationBase
                            + "receipt", new URI(base + "receipt/"
                        + identifier.toString()), "receipt", Representation.RESTBUCKS_MEDIATYPE));
                links.add(new LinkRepresentation(relationBase + Representation.SELF_REL_VALUE,
                        orderUri, "self", Representation.RESTBUCKS_MEDIATYPE));
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
    
     public static OrderRepresentation createRepresentation(Order order, LinkRepresentation... links) {
        OrderRepresentation result = new OrderRepresentation(links);
        
        result.setLocation(order.location.name());
        List<ItemRepresentation> items = new ArrayList<ItemRepresentation>(order.items.size());
        for(Item i : order.items) {
            items.add(new ItemRepresentation(i));
        }
        result.setItems(items);
        result.setCost(order.calculateCosts());
        result.setStatus(order.status.name());
        return result;
    }
    
    
    public OrderRepresentation create(OrderRepresentation representation,
            URI baseUri) throws Exception {

        
        Order order = new Order();
        order.status = OrderStatus.UNPAID;
        order.items = new ArrayList<Item>(representation.getItems().size());
        for(ItemRepresentation i: representation.getItems()) {
         order.items.add(i.getItem());
        }        
        order.location = Location.valueOf(representation.getLocation());
       
        Long id = service.createOrder(order);
        
        return createRepresentation(order, getHypermedia(id.toString(),
                baseUri, order.status));
    }

    public OrderRepresentation read(String identifier, URI baseUri)
        throws Exception {
        
        Order order = service.readOrder(Long.parseLong(identifier));
        
        return createRepresentation(order, getHypermedia(identifier.toString(),
                baseUri, order.status));
    }
   

    public OrderRepresentation update(OrderRepresentation representation,
            String identifier, URI baseUri) throws Exception {
        Order order = new Order();
        order.status = OrderStatus.valueOf(representation.getStatus());
        order.items = new ArrayList<Item>(representation.getItems().size());
        for(ItemRepresentation i: representation.getItems()) {
            order.items.add(i.getItem());
        }
        order.location = Location.valueOf(representation.getLocation());
        
        Order result = service.updateOrder(order, Long.parseLong(identifier));
        return createRepresentation(result, getHypermedia(identifier,
                baseUri, result.status));
    }
    
    
    public OrderRepresentation delete(String identifier, URI baseUri)
            throws Exception {
        
        Order order = service.readOrder(Long.parseLong(identifier));
        
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
        
        return createRepresentation(order, getHypermedia(identifier, baseUri,
                order.status));
        
    }
}
