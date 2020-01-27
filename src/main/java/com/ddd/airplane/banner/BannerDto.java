package com.ddd.airplane.banner;

import lombok.Data;

@Data
public class BannerDto {
    private Long id;
    private String title;
    private String imageUrl;

    public BannerDto(Long id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
