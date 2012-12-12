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
    private int quantitiy;
    private Order.Milk milk;
    private Order.Size size;
    private Order.Shots shots;
    
    public Item(){}
    
    public Order.Drink getName() {
        return name;
    }

    public void setName(Order.Drink name) {
        this.name = name;
    }

    public int getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(int quantitiy) {
        this.quantitiy = quantitiy;
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
