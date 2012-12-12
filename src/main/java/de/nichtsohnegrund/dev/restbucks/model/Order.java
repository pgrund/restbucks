package de.nichtsohnegrund.dev.restbucks.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Order {
    
    public long id;
    public Location location;
    public OrderStatus status;
    public Double costs;
    
    public List<Item> items = new ArrayList<Item>();

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getCosts() {
        return costs;
    }

    public void setCosts(Double costs) {
        this.costs = costs;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public enum Drink {
        LATTE, CAPUCCINO, ESPRESSO, TEA;
    }
    public enum Milk {
        SKIM, SEMI, WHOLE;
    }
    public enum Size {
        SMALL, MEDIUM, LARGE;
    }
    public enum Shots {
        SINGLE, DOUBLE, TRIPLE;
    }
    
}
