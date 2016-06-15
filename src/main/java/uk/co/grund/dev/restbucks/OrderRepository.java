package uk.co.grund.dev.restbucks;

import uk.co.grund.dev.restbucks.exceptions.InvalidOrderException;
import uk.co.grund.dev.restbucks.exceptions.NoSuchOrderException;
import uk.co.grund.dev.restbucks.exceptions.OrderDeletionException;
import uk.co.grund.dev.restbucks.model.Item;
import uk.co.grund.dev.restbucks.model.Order;
import uk.co.grund.dev.restbucks.model.OrderStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.springframework.stereotype.Repository;
import uk.co.grund.dev.restbucks.model.Location;

/**
 * Business logic for administration of {@link Order} objects, using map for
 * non-persistent in-memory storage.
 *
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@Repository
public class OrderRepository {

    private static long counterIncr = 2;
    private static final Logger LOG = Logger.getLogger(OrderRepository.class.getName());

    private Map<Long, Order> allOrders = new HashMap<>();

    public OrderRepository() {
        allOrders.putAll(LongStream.range(1L, 5L).boxed()
                .map( (Long id) -> {
                    Order o = new Order();
                    o.id = id;
                    o.status = OrderStatus.UNPAID;
                    o.location = (id < 2 ? Location.IN_SHOP : Location.TAKE_AWAY );
                    o.items.add(new Item(Order.Drink.LATTE, 5, Order.Milk.SKIM, Order.Size.SMALL, Order.Shots.SINGLE));
                    return o;
                }).collect(Collectors.toMap(o -> o.id, Function.identity())));
    }

    private Order validateOrder(Order order) throws InvalidOrderException {
        if (order == null) {
            LOG.info("null order");
            throw new InvalidOrderException();
        }
        if (order.location == null) {
            LOG.info("null location");
            throw new InvalidOrderException();
        }
        if (order.items == null || order.items.isEmpty()) {
            LOG.info("null items");
            throw new InvalidOrderException();
        }
        for (Item i : order.items) {
            validateItem(i);
        }
        return order;
    }

    private Item validateItem(Item item) throws InvalidOrderException {
        if (item == null) {
            LOG.info("null item");
            throw new InvalidOrderException();
        }
        if (item.getName() == null) {
            LOG.info("null item.name");
            throw new InvalidOrderException();
        }
        if (item.getMilk() == null) {
            LOG.info("null item.milk");
            throw new InvalidOrderException();
        }
        if (item.getQuantity() <= 0) {
            LOG.info("null item.quantity");
            throw new InvalidOrderException();
        }
        if (item.getSize() == null) {
            LOG.info("null item.size");
            throw new InvalidOrderException();
        }
        return item;
    }

    public void setAllOrders(Map<Long, Order> allOrders) {
        this.allOrders = allOrders;
    }

    /**
     * Validate and add a new {@link Order} to the list of known orders.
     *
     * @param order {@link Order}
     * @return uniqe identifier as {@link UUID}
     * @throws InvalidOrderException
     */
    public Order createOrder(Order order) throws InvalidOrderException {

        validateOrder(order);
        order.status = OrderStatus.UNPAID;

        Long identifier = counterIncr++;
        order.id = identifier;
        allOrders.put(identifier, order);

        return order;
    }

    /**
     * Read a known {@link Order} by its unique id, or throw an exception if
     * unknown.
     *
     * @param id uniqe identifier
     * @return known {@link Order} or <strong>null</strong>
     * @throws NoSuchOrderException
     */
    public Order readOrder(Long id) throws NoSuchOrderException {
        Order order = allOrders.get(id);

        if (order == null) {
            throw new NoSuchOrderException();
        }
        return order;
    }

    /**
     * Replace a known {@link Order} known by its uniqe identifier.
     *
     * @param order new data as {@link Order} for updating
     * @param id unique identifier of existing order
     * @return updated {@link Order}
     * @throws NoSuchOrderException if identifier is not known
     * @throws IllegalStateException if not identifier present
     * @throws InvalidOrderException if passed {@link Order} is not valid
     */
    public Order updateOrder(Order order, Long id) throws NoSuchOrderException,
            IllegalStateException, InvalidOrderException {
        if (id == null) {
            throw new IllegalStateException("no id present");
        }

        Order old = allOrders.get(id);
        if (old == null) {
            throw new NoSuchOrderException();
        }
        // changes only for unpaid orders
        if (old.status != OrderStatus.UNPAID) {
            throw new InvalidOrderException();
        }

        allOrders.merge(id, old, (BiFunction<? super Order, ? super Order, ? extends Order>) validateOrder(order));
        return order;
    }

    /**
     * Delete a known {@link Order}, i.e. set its status according to state
     * machine.
     *
     * @param id uniqe identifier
     * @throws NoSuchOrderException if id unknown
     * @throws OrderDeletionException if state machine does not allow deleting
     */
    public void deleteOrder(Long id) throws OrderDeletionException,
            NoSuchOrderException {
        Order order = readOrder(id);

        switch (order.status) {
            case UNPAID:
                order.status = OrderStatus.CANCELED;
                break;
            case READY:
                order.status = OrderStatus.DELIVERED;
                break;
            default:
                throw new OrderDeletionException();
        }
        allOrders.put(id, order);
    }

}
