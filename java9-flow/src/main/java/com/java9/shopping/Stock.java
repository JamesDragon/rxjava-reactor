package com.java9.shopping;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Stock {
    private final ConcurrentHashMap<Product, StockItem> map = new ConcurrentHashMap<>(16);

    public StockItem getItem(Product product) {
        //不存在则存储商品
        map.putIfAbsent(product, new StockItem());
        return map.get(product);
    }

    public void store(Product product, long amount) {
        getItem(product).store(amount);
    }

    public void remove(Product product, long amount) throws IllegalAccessException {
        if (getItem(product).remove(amount) != amount) {
            throw new IllegalAccessException("没库存了");
        }
    }


}
