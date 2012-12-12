/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev;

import java.text.ParseException;
import java.util.Locale;
import java.util.UUID;
import org.springframework.format.Formatter;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class UUIDFormatter implements Formatter<UUID> {

    @Override
    public String print(UUID t, Locale locale) {
        return t.toString();
    }

    @Override
    public UUID parse(String string, Locale locale) throws ParseException {
        return UUID.fromString(string);
    }

    
}
