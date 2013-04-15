/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks.model;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Item {

    private Order.Drink name;
    private int quantity;
    private Order.Milk milk;
    private Order.Size size;
    private Order.Shots shots;
    
    public Item(){}
    
    public Item(Order.Drink drink, int quantity) {
        this();
        this.name = drink;
        this.quantity = quantity;
    }
    public Item(Order.Drink name, int quantity, Order.Milk milk, 
            Order.Size size, Order.Shots shots) {
        this(name, quantity);
        this.milk = milk;
        this.size = size;
        this.shots = shots;        
    }
    
    public Order.Drink getName() {
        return name;
    }

    public void setName(Order.Drink name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantitiy) {
        this.quantity = quantitiy;
    }

    public Order.Milk getMilk() {
        return milk;
    }

    public void setMilk(Order.Milk milk) {
        this.milk = milk;
    }

    public Order.Size getSize() {
        return size;
    }

    public void setSize(Order.Size size) {
        this.size = size;
    }

    public Order.Shots getShots() {
        return shots;
    }

    public void setShots(Order.Shots shots) {
        this.shots = shots;
    }
}
