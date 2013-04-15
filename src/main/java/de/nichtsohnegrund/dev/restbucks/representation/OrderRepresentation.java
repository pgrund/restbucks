package de.nichtsohnegrund.dev.restbucks.representation;

import de.nichtsohnegrund.dev.restbucks.model.Order;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAX-RS representation for {@link Order}. 
 * 
 * @see Representation
 * @author pgrund
 */
@XmlRootElement(name="order",namespace=Representation.RESTBUCKS_NAMESPACE)
@XmlType(name = "order", namespace = Representation.RESTBUCKS_NAMESPACE)
public class OrderRepresentation extends Representation {

    
    private String location;
    private String status;
    private Double cost;
    private List<ItemRepresentation> items;
    
    public OrderRepresentation() {}
    
    public OrderRepresentation(LinkRepresentation ... links) {
        this.links = Arrays.asList(links);
    }
   
    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE, required = true)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlElement(required = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE, required=true)
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @XmlElement(namespace=Representation.RESTBUCKS_NAMESPACE)
    public List<ItemRepresentation> getItems() {
        return items;
    }

    public void setItems(List<ItemRepresentation> items) {
        this.items = items;
    }
    
    public LinkRepresentation getUpdateLink() {
        return getLinkByName("update");
    }
}