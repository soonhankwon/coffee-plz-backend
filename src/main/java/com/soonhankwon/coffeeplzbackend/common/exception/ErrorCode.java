package com.soonhankwon.coffeeplzbackend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    PREVIOUS_ORDER_EXISTS(HttpStatus.BAD_REQUEST, "이전 주문 결제 후 주문이 가능합니다."),
    POINT_INSUFFICIENT(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),
    POINT_INVALID(HttpStatus.BAD_REQUEST, "충전 포인트는 0보다 커야합니다."),
    ITEM_UPDATE_PRICE_INVALID(HttpStatus.BAD_REQUEST, "아이템의 가격은 0보다 커야합니다."),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "중복된 아이디가 존재합니다."),
    EMPTY_ORDER_LIST(HttpStatus.BAD_REQUEST, "주문 목록이 비어있습니다."),
    ORDER_STATUS_INVALID(HttpStatus.BAD_REQUEST, "주문 상태가 유효하지 않습니다." );

    private final HttpStatus httpStatus;
    private final String message;
}
