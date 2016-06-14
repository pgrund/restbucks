/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.grund.dev.restbucks.model;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author pgrund
 */
public enum OrderStatus {
  @XmlEnumValue(value="unpaid")
  UNPAID,
  @XmlEnumValue(value="preparing")
  PREPARING,
  @XmlEnumValue(value="ready")
  READY,
  @XmlEnumValue(value="delivered")
  DELIVERED,
  @XmlEnumValue(value="canceled")
  CANCELED;
}
