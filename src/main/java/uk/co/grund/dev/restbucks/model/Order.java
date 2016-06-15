package uk.co.grund.dev.restbucks.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Order {

    public long id;
    public Location location;
    public OrderStatus status = OrderStatus.UNPAID;
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

    public Double calculateCosts() {
        double total = 0.0;
        for (Item item : items) {
            if (item != null && item.getName() != null) {
                total += item.getName().getPrice();
            }
        }
        return total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public enum Drink {

        @XmlEnumValue(value = "espresso")
        ESPRESSO(1.5),
        @XmlEnumValue(value = "latte")
        LATTE(2.0),
        @XmlEnumValue(value = "cappuccino")
        CAPPUCCINO(2.0),
        @XmlEnumValue(value = "flatWhite")
        FLAT_WHITE(2.0);
        final double price;

        Drink(double price) {
            this.price = price;
        }

        public double getPrice() {
            return this.price;
        }
        public boolean isEspresso() {
            return this == ESPRESSO;
        }
        public boolean isLatte() {
            return this == LATTE;
        }
        public boolean isCappuccino() {
            return this == CAPPUCCINO;
        }
        public boolean isFlatWhite() {
            return this == FLAT_WHITE;
        }
    }

    public enum Milk {

        @XmlEnumValue(value = "skim")
        SKIM,
        @XmlEnumValue(value = "semi")
        SEMI,
        @XmlEnumValue(value = "whole")
        WHOLE;
        
        public boolean isSkim(){
            return this == SKIM;
        }
        public boolean isSemi(){
            return this == SEMI;
        }
        public boolean isWhole(){
            return this == WHOLE;
        }
    }

    public enum Size {

        @XmlEnumValue(value = "small")
        SMALL, 
        @XmlEnumValue(value = "medium")
        MEDIUM, 
        @XmlEnumValue(value = "large")
        LARGE;
        
        public boolean isSmall(){
            return this == SMALL;
        }
        public boolean isMedium(){
            return this == MEDIUM;
        }
        public boolean isLarge(){
            return this == LARGE;
        }
    }

    public enum Shots {
        @XmlEnumValue(value = "single")
        SINGLE,
        @XmlEnumValue(value = "double")
        DOUBLE, 
        @XmlEnumValue(value = "triple")
        TRIPLE;
        
        public boolean isSingle() {
            return this == SINGLE;
        }
        public boolean isDouble(){
            return this == DOUBLE;
        }
        public boolean isTriple(){
            return this == TRIPLE;
        }
    }
}
