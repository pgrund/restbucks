/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks;

import de.nichtsohnegrund.dev.restbucks.activity.PaymentActivity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@Path("/receipt")
public class ReceiptResource {
    
    private @Context UriInfo uriInfo;
    
    private PaymentActivity activity;

    public void setActivity(PaymentActivity activity) {
        this.activity = activity;
    }
    
    @GET
    @Path("/{orderId}")
    public Response getReceiptForOrder(@PathParam("orderId") String orderId) {
        try {
            return Response.ok(this.activity.getReceipt(uriInfo.getBaseUri(), orderId)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
