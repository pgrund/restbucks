/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks.model;

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
}
