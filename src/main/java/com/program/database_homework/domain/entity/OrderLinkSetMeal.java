package com.program.database_homework.domain.entity;

public class OrderLinkSetMeal {
    private Integer orderId;

    private Integer setMealId;

    private Integer count;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(Integer setMealId) {
        this.setMealId = setMealId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}