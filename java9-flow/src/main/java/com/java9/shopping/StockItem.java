package com.java9.shopping;

import java.util.concurrent.atomic.AtomicLong;

public class StockItem {
    private AtomicLong atomicLong = new AtomicLong(0);

    /**
     * 加库存
     *
     * @param amount
     */
    public void store(long amount) {
        atomicLong.accumulateAndGet(amount, (pre, mount) -> pre + mount);
    }

    /**
     * 减库存
     *
     * @param amount
     * @return
     */
    public long remove(long amount) {
        //对象的属性可以从Lambda表达式中逃逸出去
        class RemoveData {
            long remove;
        }
        //为了在Lambda表达是中完成赋值过程
        RemoveData removeData = new RemoveData();
        atomicLong.accumulateAndGet(amount, (pre, mount) -> pre >= mount ? pre - (removeData.remove = mount) : (removeData.remove = 0L));
        return removeData.remove;
    }
}
