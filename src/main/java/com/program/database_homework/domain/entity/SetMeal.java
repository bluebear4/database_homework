package com.program.database_homework.domain.entity;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Builder
public class SetMeal {
    private Integer id;

    private String setMealName;

    private BigDecimal price;

    private Date creatTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSetMealName() {
        return setMealName;
    }

    public void setSetMealName(String setMealName) {
        this.setMealName = setMealName == null ? null : setMealName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetMeal setMeal = (SetMeal) o;
        return Objects.equals(id, setMeal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}