package com.ddd.airplane.banner;

import lombok.Data;

@Data
public class BannerDto {
    private Long id;
    private String title;
    private String thumbnailUrl;

    public BannerDto(Long id, String title, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }
}
