/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.bank.model;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Account {

    public long id;
    
    public double balance;
    
    public Customer customer;
    
    public double withdraw(double amount) {
        balance -= amount;
        return balance;
    }
    
    public double deposit(double amount) {
        balance += amount;
        return balance;
    }
}
