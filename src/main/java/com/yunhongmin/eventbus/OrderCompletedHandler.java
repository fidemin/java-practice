package com.yunhongmin.eventbus;

public class OrderCompletedHandler implements EventHandler<String> {
    @Override
    public void handle(Event<String> event) {
        System.out.printf("completed with %s%n", event.value());
    }
}
