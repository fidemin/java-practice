package com.yunhongmin.eventbus;

public class OrderCreatedEventHandler implements EventHandler<String> {
    @Override
    public void handle(Event<String> event) {
        // how to handle V? How can I know V type?
        System.out.printf("order created: %s%n", event.value());
    }
}
