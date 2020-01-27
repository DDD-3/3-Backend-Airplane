package com.ddd.airplane.home;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import com.ddd.airplane.banner.BannerDto;
import com.ddd.airplane.chat.message.MessageService;
import com.ddd.airplane.chat.room.Room;
import com.ddd.airplane.chat.room.RoomDto;
import com.ddd.airplane.chat.room.RoomService;
import com.ddd.airplane.common.ListDto;
import com.ddd.airplane.common.PageInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HomeApiController {
    private final RoomService roomService;
    private final MessageService messageService;

    @GetMapping("/v1/home")
    @ResponseStatus(HttpStatus.OK)
    public HomeDto home(@CurrentAccount Account account) {
        // TODO : 2차
        HomeComponentDto<List<BannerDto>> topBanner = new HomeComponentDto<>(
                HomeComponentStyle.TOP_BANNER,
                List.of(
                        new BannerDto(1L, "혼자 방송 볼 때\n 심심하다면?\n 사바사!", null),
                        new BannerDto(2L, "사이더들과\n 오늘 밤\n 달려요!", null),
                        new BannerDto(3L, "구독 방송\n 핫한 방송\n 한번에!", null)));

        // TODO : 2차
        HomeComponentDto<BannerDto> rectangleBanner = new HomeComponentDto<>(
                HomeComponentStyle.RECTANGLE_BANNER,
                new BannerDto(4L, "빈 배너", null));

        List<Room> recentMessageRooms = roomService.getRecentMessagedRooms(account, new PageInfo(1, 10));
        ListDto<RoomDto> recentMessageRoomsDto = convert(recentMessageRooms);
        HomeComponentDto<ListDto<RoomDto>> horizontal = new HomeComponentDto<>(
                HomeComponentStyle.HORIZONTAL,
                HomeComponentTitle.RECENT_MESSAGE,
                recentMessageRoomsDto);

        // TODO : 1차
        HomeComponentDto<ListDto<RoomDto>> grid = new HomeComponentDto<>(
                HomeComponentStyle.GRID,
                HomeComponentTitle.HOT,
                new ListDto<>(null));

        // TODO : 1차
        HomeComponentDto<ListDto<RoomDto>> rank = new HomeComponentDto<>(
                HomeComponentStyle.RANK,
                HomeComponentTitle.MOST_JOIN,
                new ListDto<>(null));

        return new HomeDto(List.of(topBanner, rectangleBanner, horizontal, grid, rank));
    }

    private ListDto<RoomDto> convert(List<Room> rooms) {
        List<RoomDto> roomDtoList = rooms.stream()
                .map(r -> new RoomDto(
                        r.getRoomId(),
                        r.getSubject().getName(),
                        r.getSubject().getDescription(),
                        null,
                        r.getSubject().getSubscribeCount(),
                        r.getUserCount()
                )).collect(Collectors.toList());

        return new ListDto<>(roomDtoList);
    }

    @Data
    public static class HomeDto{
        private List<HomeComponentDto> list;

        HomeDto(List<HomeComponentDto> list) {
            this.list = list;
        }
    }

    @Data
    public static class HomeComponentDto<T> {
        private String style;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String title;
        private T item;

        // 배너
        HomeComponentDto(HomeComponentStyle style, T item) {
            this.style = style.getCode();
            this.item = item;
        }

        // 방
        HomeComponentDto(HomeComponentStyle style, HomeComponentTitle title, T item) {
            this.style = style.getCode();
            this.title = title.getName();
            this.item = item;
        }
    }

    public enum HomeComponentStyle {
        TOP_BANNER("topBanner"),
        RECTANGLE_BANNER("rectangleBanner"),
        HORIZONTAL("horizontal"),
        GRID("grid"),
        RANK("rank");

        private String code;

        HomeComponentStyle(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum HomeComponentTitle {
        RECENT_MESSAGE("최근 본 방송"),
        MOST_JOIN("많이 참여하는 방"),
        HOT("핫한 방송");

        private String name;

        HomeComponentTitle(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
