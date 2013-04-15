/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nichtsohnegrund.dev.restbucks.representation;

import de.nichtsohnegrund.dev.restbucks.model.Item;
import de.nichtsohnegrund.dev.restbucks.model.Order;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author grund
 */
@XmlRootElement(name = "item", namespace = Representation.RESTBUCKS_NAMESPACE)
@XmlType(name = "item", namespace = Representation.RESTBUCKS_NAMESPACE)
public class ItemRepresentation extends Representation {
    
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private int quantity;
    
    @XmlElement
    private String milk;
    @XmlElement    
    private String size;
    @XmlElement    
    private String shots;
    
    public ItemRepresentation() {}

    public ItemRepresentation(Item item) {
        this.name = item.getName().toString();
        this.quantity = item.getQuantity();
        if(item.getMilk() != null) {
            this.milk = item.getMilk().toString();
        }
        if(item.getSize() != null) {
            this.size = item.getSize().toString();
        }
        if(item.getShots()!= null) {
            this.shots = item.getShots().toString();
        }
    }
    
    public ItemRepresentation(Item item, LinkRepresentation... links) {
        this(item);
        this.links = Arrays.asList(links);
    }
    
    @XmlTransient
    public Item getItem() {
        Item result = new Item();
        result.setName(Order.Drink.valueOf(this.name));
        result.setQuantity(this.quantity);
        result.setMilk(Order.Milk.valueOf(this.milk));
        result.setSize(Order.Size.valueOf(this.size));
        result.setShots(Order.Shots.valueOf(this.shots));
        return result;
    }
}