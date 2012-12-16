package de.nichtsohnegrund.dev.restbucks;

import de.nichtsohnegrund.dev.restbucks.activity.OrderActivity;
import de.nichtsohnegrund.dev.restbucks.exceptions.InvalidOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.NoSuchOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.OrderDeletionException;
import de.nichtsohnegrund.dev.restbucks.exceptions.UpdateException;
import de.nichtsohnegrund.dev.restbucks.representation.OrderRepresentation;
import de.nichtsohnegrund.dev.restbucks.representation.Representation;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * JAX-RS Resource for {@link OrderRepresentation}.
 * 
 * @see OrderActivity
 * @see OrderService
 * 
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@Path("/order")
public class OrderResource {
   
    private @Context UriInfo uriInfo;
    
    private OrderActivity activity;

    public void setActivity(OrderActivity activity) {
        this.activity = activity;
    }
    
    
    @GET
    @Path("/{orderId}")
    @Produces(Representation.RESTBUCKS_MEDIATYPE)
    public Response getOrder(@PathParam("orderId") String orderId){
        try {
            System.out.println(">>" + orderId +"<<");
            OrderRepresentation representation = activity
                    .read(orderId, uriInfo.getBaseUri());
            return Response.ok().entity(representation).build();
        } catch (NoSuchOrderException nsoe) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getLocalizedMessage()).build();
        }
    }
    
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.TEXT_HTML)
    public OrderRepresentation getOrderAsHTML(@PathParam("orderId") String orderId)
        throws WebApplicationException {
        try {
            OrderRepresentation order = activity
                    .read(orderId, uriInfo.getBaseUri());
            return order;
        } catch (NoSuchOrderException nsoe) {
            throw new NotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(500);
        }
    }
    
    @POST
    @Consumes(Representation.RESTBUCKS_MEDIATYPE)
    @Produces(Representation.RESTBUCKS_MEDIATYPE)
    public Response createOrder(OrderRepresentation order) {
        try {
           OrderRepresentation response = activity
                   .create(order, uriInfo.getBaseUri());
           return Response.created(response.getUpdateLink().getUri())
                   .entity(response).build();
        } catch (InvalidOrderException ioe) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("/{orderId}")
    @Produces(Representation.RESTBUCKS_MEDIATYPE)
    public Response removeOrder(@PathParam("orderId")String orderId) {
        try {
            OrderRepresentation removedOrder = activity
                    .delete(orderId, uriInfo.getBaseUri());
            return Response.ok().entity(removedOrder).build();
        } catch (NoSuchOrderException noe){
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (OrderDeletionException ode){
            return Response.status(405).header("Allow", "GET").build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("/{orderId}")
    @Consumes(Representation.RESTBUCKS_MEDIATYPE)
    @Produces(Representation.RESTBUCKS_MEDIATYPE)
    public Response updateOrder(@PathParam("orderId") String orderId, 
        OrderRepresentation orderRepresentation){
        
        try {
            OrderRepresentation response = activity
                    .update(orderRepresentation, orderId, uriInfo.getBaseUri());
            return Response.ok().entity(response).build();
        } catch (InvalidOrderException ioe) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NoSuchOrderException nsoe) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (UpdateException ue) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
