package com.yunhongmin.eventbus;

public record Event<V>(EventType type, V value) {
}
