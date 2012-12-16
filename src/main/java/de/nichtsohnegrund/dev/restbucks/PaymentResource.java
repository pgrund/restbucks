/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks;

import de.nichtsohnegrund.dev.restbucks.activity.PaymentActivity;
import de.nichtsohnegrund.dev.restbucks.exceptions.NoSuchOrderException;
import de.nichtsohnegrund.dev.restbucks.representation.PaymentRepresentation;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@Path("/payment")
public class PaymentResource {
    
    private @Context UriInfo uriInfo;
    
    private PaymentActivity activity;

    public void setActivity(PaymentActivity activity) {
        this.activity = activity;
    }
    

    @PUT
    @Path("/{orderId}")
    public Response payOrder(@PathParam("orderId") String orderId, PaymentRepresentation payment) {
        try{
            PaymentRepresentation result = this.activity.payOrder(payment, 
                    uriInfo.getBaseUri(), orderId);
            return Response.created(uriInfo.getRequestUri()).entity(result).build();
        }catch (NoSuchOrderException nso) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();
        }
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
