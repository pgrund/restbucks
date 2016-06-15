/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.grund.dev.restbucks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@ResponseStatus( value = HttpStatus.CONFLICT, reason = "order state does not allow deletion")
public class OrderDeletionException extends OrderException {

}
