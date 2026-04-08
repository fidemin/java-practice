package com.yunhongmin.eventbus;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {

    @Test
    void publishedEventIsReceivedBySubscriber() {
        EventBus eventBus = new EventBus();
        List<String> received = new ArrayList<>();

        eventBus.subscribe(EventType.ORDER_CREATED, (EventHandler<String>) event -> received.add(event.value()));
        eventBus.publish(new Event<>(EventType.ORDER_CREATED, "order-1"));

        assertEquals(List.of("order-1"), received);
    }

    @Test
    void unsubscribedHandlerDoesNotReceiveEvents() {
        EventBus eventBus = new EventBus();
        List<String> received = new ArrayList<>();

        EventHandler<String> handler = event -> received.add(event.value());
        eventBus.subscribe(EventType.ORDER_CREATED, handler);
        eventBus.unsubscribe(EventType.ORDER_CREATED, handler);
        eventBus.publish(new Event<>(EventType.ORDER_CREATED, "order-1"));

        assertTrue(received.isEmpty());
    }

    @Test
    void multipleSubscribersAllReceiveEvent() {
        EventBus eventBus = new EventBus();
        List<String> received = new ArrayList<>();

        eventBus.subscribe(EventType.ORDER_CREATED, (EventHandler<String>) event -> received.add("handler1"));
        eventBus.subscribe(EventType.ORDER_CREATED, (EventHandler<String>) event -> received.add("handler2"));
        eventBus.publish(new Event<>(EventType.ORDER_CREATED, "order-1"));

        assertEquals(2, received.size());
        assertTrue(received.containsAll(List.of("handler1", "handler2")));
    }

    @Test
    void publishToUnsubscribedEventTypeDoesNothing() {
        EventBus eventBus = new EventBus();
        List<String> received = new ArrayList<>();

        eventBus.subscribe(EventType.ORDER_CREATED, (EventHandler<String>) event -> received.add(event.value()));
        eventBus.publish(new Event<>(EventType.ORDER_COMPLETED, "order-1"));

        assertTrue(received.isEmpty());
    }
}
