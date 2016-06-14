/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.grund.dev.restbucks.model;

import java.util.Date;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Receipt {

    private Date paid;
    private Double amount;

    public Receipt(Double amount) {
        this.amount = amount;
        this.paid = new Date();
    }

    public Date getPaid() {
        return paid;
    }

    public void setPaid(Date paid) {
        this.paid = paid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    
}
