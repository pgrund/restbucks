/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev.restbucks;

import de.nichtsohnegrund.dev.restbucks.exceptions.InvalidOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.NoSuchOrderException;
import de.nichtsohnegrund.dev.restbucks.exceptions.OrderDeletionException;
import de.nichtsohnegrund.dev.restbucks.exceptions.OrderException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class OrderExceptionMapper implements ExceptionMapper<OrderException>{

    @Override
    public Response toResponse(OrderException e) {
        if(e instanceof NoSuchOrderException){
           return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(e instanceof InvalidOrderException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if( e instanceof OrderDeletionException){
            return Response.status(405).header("Allow", "GET").build();
        }
        return Response.serverError().build();
    }
    
}
