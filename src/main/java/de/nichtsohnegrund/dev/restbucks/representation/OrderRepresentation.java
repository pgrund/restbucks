package de.nichtsohnegrund.dev.restbucks.representation;

import de.nichtsohnegrund.dev.restbucks.model.Item;
import de.nichtsohnegrund.dev.restbucks.model.Order;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAX-RS representation for {@link Order}. 
 * 
 * @see Representation
 * @author pgrund
 */
@XmlRootElement(name="order",namespace=Representation.RESTBUCKS_NAMESPACE)
public class OrderRepresentation extends Representation {

    
    private String location;
    private String status;
    private Double cost;
    private List<Item> items;
    
    public OrderRepresentation() {}
    
    public OrderRepresentation(Order order, LinkRepresentation... links) {
        this();
        this.location = order.location.name();
        this.items = order.items;
        this.cost = order.calculateCosts();
        this.status = order.status.name();
        this.links = Arrays.asList(links);
    }

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public LinkRepresentation getUpdateLink() {
        return getLinkByName("update");
    }
}
