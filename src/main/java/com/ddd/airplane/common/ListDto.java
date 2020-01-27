package com.ddd.airplane.common;

import lombok.Data;

import java.util.List;

@Data
public class ListDto<T> {
    private List<T> list;

    public ListDto(List<T> list) {
        this.list = list;
    }
}
