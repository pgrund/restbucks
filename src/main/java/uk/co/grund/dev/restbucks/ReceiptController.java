/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.grund.dev.restbucks;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.grund.dev.restbucks.exceptions.NoSuchOrderException;
import uk.co.grund.dev.restbucks.exceptions.NoSuchReceiptException;
import uk.co.grund.dev.restbucks.exceptions.PaymentException;
import uk.co.grund.dev.restbucks.model.Order;
import uk.co.grund.dev.restbucks.model.Payment;
import uk.co.grund.dev.restbucks.model.Receipt;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@RestController
@ExposesResourceFor(Receipt.class)
@RequestMapping(path = "/receipts")
public class ReceiptController {

    private final static Logger LOG = Logger.getLogger(ReceiptController.class.getName());

    private final static String RELATION_BASE = "http://localhost:8080/restbucks.html#relation-";

    private final EntityLinks entityLinks;
    private final ReceiptRepository service;

    @Autowired
    public ReceiptController(EntityLinks entityLinks, ReceiptRepository service) {
        this.entityLinks = entityLinks;
        this.service = service;
    }

    @RequestMapping(
            path = "/{orderId}",
            method = RequestMethod.PUT
    )
    public Resource payOrder(@PathVariable("orderId") String orderId,
            Payment payment) throws NoSuchOrderException,
            PaymentException {

        this.service.payOrder(payment, Long.parseLong(orderId));
        return new Resource<>(null,
                entityLinks.linkForSingleResource(Order.class, orderId)
                .withRel(RELATION_BASE + "order"),
                entityLinks.linkForSingleResource(Receipt.class, orderId)
                .withRel(RELATION_BASE + "receipt")
        );
    }

    @RequestMapping(
            path = "/{orderId}",
            method = RequestMethod.GET
    )
    public Resource<Receipt> getReceiptForOrder(@PathVariable("orderId") String orderId)
            throws NoSuchReceiptException {

        return new Resource<>(
                this.service.getReceiptForOrder(Long.parseLong(orderId)),
                entityLinks.linkForSingleResource(Order.class, orderId)
                .withRel(RELATION_BASE + "order")
        );

    }

}
