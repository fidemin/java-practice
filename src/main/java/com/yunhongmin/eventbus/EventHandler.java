package com.yunhongmin.eventbus;

public interface EventHandler<V> {
    void handle(Event<V> event);
}
