package com.yunhongmin.eventbus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBus {
    private final Map<EventType, Set<EventHandler<?>>> handlerMap = new HashMap<>();

    synchronized public void subscribe(EventType eventType, EventHandler<?> eventHandler) {
        Set<EventHandler<?>> handlerSet = handlerMap.computeIfAbsent(eventType, k -> new HashSet<>());
        handlerSet.add(eventHandler);
    }

    synchronized public void unsubscribe(EventType eventType, EventHandler<?> eventHandler) {
        Set<EventHandler<?>> handlerSet = handlerMap.get(eventType);
        if (handlerSet == null) return;
        handlerSet.remove(eventHandler);
    }

    @SuppressWarnings("unchecked")
    public void publish(Event<?> event) {
        EventType eventType = event.type();

        // handle race condition
        Set<EventHandler<?>> handlerSet;
        synchronized (this) {
            Set<EventHandler<?>> snapshot = handlerMap.get(eventType);
            if (snapshot == null) return;
            handlerSet = new HashSet<>(snapshot);
        }

        handlerSet.forEach(handler -> {
            try {
                ((EventHandler<Object>) handler).handle((Event<Object>) event);
            } catch (Exception e) {
                // log handler error
            }
        });
    }
}
