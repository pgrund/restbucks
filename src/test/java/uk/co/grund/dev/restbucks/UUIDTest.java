/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.grund.dev.restbucks;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class UUIDTest {
    
    @Test
    public void testUUIDFromString() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.fromString(id1.toString());
        Assert.assertEquals(id1, id2);
    }

}
