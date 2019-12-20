package com.ddd.airplane.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PageContent<T> {
    private List<T> items;
    private PageInfo pageInfo;

    public PageContent(List<T> items, PageInfo pageInfo) {
        this.items = items;
        this.pageInfo = pageInfo;
    }
}
