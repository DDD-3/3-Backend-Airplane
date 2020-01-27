package com.ddd.airplane.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageInfo {
    @Min(1)
    private Integer pageNum = 1;
    @Max(100)
    private Integer pageSize = 10;

    @JsonIgnore
    public int getLimit() {
        return pageSize;
    }

    @JsonIgnore
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

    public PageInfo(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
