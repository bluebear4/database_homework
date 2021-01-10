package com.program.database_homework.domain.entity;

import lombok.Builder;

@Builder
public class SetMealLinkFood {
    private Integer foodId;

    private Integer setMealId;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(Integer setMealId) {
        this.setMealId = setMealId;
    }
}