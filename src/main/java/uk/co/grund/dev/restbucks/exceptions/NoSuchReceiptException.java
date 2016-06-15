/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.grund.dev.restbucks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no receipt found for this order")
public class NoSuchReceiptException extends RuntimeException {

}
