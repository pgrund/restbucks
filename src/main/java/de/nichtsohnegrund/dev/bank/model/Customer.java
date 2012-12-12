/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.bank.model;

import java.util.Set;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class Customer {

    protected long id;
    
    public String firstname;
    public String lastname;
    
    public String address;
    
    public Set<Account> accounts;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
