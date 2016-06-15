/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.grund.dev.restbucks.model;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public enum Location {
    @XmlEnumValue(value="takeAway")
    TAKE_AWAY,
    @XmlEnumValue(value="inShop")
    IN_SHOP;
    
    public boolean isTakeAway() {
        return this == TAKE_AWAY;
    }
    public boolean isInShop() {
        return this == IN_SHOP;
    }

    @Override
    public String toString() {
        switch (this) {
            case TAKE_AWAY:
                return "TAKE_AWAY";
            case IN_SHOP:
                return "IN_SHOP";
            default:
                return null;
        }
        
    }
    
    
}
