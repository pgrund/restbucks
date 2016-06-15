package uk.co.grund.dev.restbucks;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.grund.dev.restbucks.exceptions.InvalidOrderException;
import uk.co.grund.dev.restbucks.exceptions.NoSuchOrderException;
import uk.co.grund.dev.restbucks.exceptions.OrderDeletionException;
import uk.co.grund.dev.restbucks.model.Order;
import uk.co.grund.dev.restbucks.model.Payment;

/**
 * JAX-RS Resource for {@link OrderRepresentation}.
 *
 * @see OrderActivity
 * @see OrderRepository
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@Controller
@RequestMapping(value = "/orders")
@ExposesResourceFor(Order.class)
public class OrderController {

    private final static Logger LOG = Logger.getLogger(OrderController.class.getName());

    private final OrderRepository service;
    private final EntityLinks entityLinks;

    @Autowired
    public OrderController(OrderRepository service, EntityLinks entityLinks) {
        this.service = service;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(
            value = "/{orderId}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<Order> getOrder(@PathVariable("orderId") String orderId)
            throws NoSuchOrderException {

        LOG.log(Level.INFO, ">>{0}<<", orderId);
        try {
            return getResource(service.readOrder(Long.parseLong(orderId)));
        } catch (NumberFormatException nfe) {
            throw new NoSuchOrderException();
        }

    }

    @RequestMapping(
            path = "/{orderId}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String getOrderAsHTML(@PathVariable("orderId") String orderId, Model model)
            throws NoSuchOrderException {
        LOG.log(Level.INFO, "entering html for order {0}", orderId);
        Order order = this.service.readOrder(Long.parseLong(orderId));

        model.addAttribute("order", order);
        model.addAttribute("links", getResource(order).getLinks());
        return "singleorder";
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Order> createOrder(Order order) throws InvalidOrderException {
        return getResource(service.createOrder(order));
    }

    @RequestMapping(
            path = "/{orderId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public HttpEntity removeOrder(@PathVariable("orderId") String orderId)
            throws OrderDeletionException, NoSuchOrderException {
        LOG.log(Level.INFO, "deleting order {0} ...", orderId);
        service.deleteOrder(Long.parseLong(orderId));
        LOG.log(Level.INFO, "successfully deleted order {0}", orderId);
        return new ResponseEntity( HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{orderId}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Resource<Order> updateOrder(@PathVariable("orderId") String orderId,
            Order order) throws NoSuchOrderException, InvalidOrderException {

        return getResource(service.updateOrder(order,
                Long.parseLong(orderId)));

    }

    public Resource<Order> getResource(Order order) {

        // self link always present
        Resource<Order> result = new Resource<>(order,
                entityLinks.linkForSingleResource(Order.class, order.id)
                .withSelfRel());

        switch (order.status) {
            case UNPAID:
                result.add(
                        entityLinks.linkToSingleResource(Order.class, order.id)
                        .withRel("cancel"),
                        entityLinks.linkToSingleResource(Order.class, order.id)
                        .withRel("update")
                );
                try {
                    result.add(ControllerLinkBuilder.linkTo(
                            ReceiptController.class.getMethod("payOrder", String.class, Payment.class),
                            order.id)
                            .withRel("pay")
                    );
                } catch (NoSuchMethodException nsme) {
                    LOG.log(Level.SEVERE, "invalid refernece to method", nsme);
                }
                break;
            case READY:
                try {
                    result.add(ControllerLinkBuilder.linkTo(
                            ReceiptController.class.getMethod("getReceiptForOrder", Long.class),
                            order.id)
                            .withRel("receipt")
                    );
                } catch (NoSuchMethodException nsme) {
                    LOG.log(Level.SEVERE, "invalid refernece to method", nsme);
                }

                break;
            case DELIVERED:
            case CANCELED:
            case PREPARING:
                break;

        }

        return result;

    }
}
