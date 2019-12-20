package com.ddd.airplane.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
public class PageInfo {
    @Min(1)
    private Integer pageNum;
    @Max(100)
    private Integer pageSize;

    @JsonIgnore
    public int getLimit() {
        return pageSize;
    }

    @JsonIgnore
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
