package com.odtrend.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    Client_Call_Error(2001, "API 호출에 실패했습니다"),
    Product_Nomalizer_Factory_Error(2002, "상품 정규화 팩토리 호출에 실패했습니다"),
    Product_Nomalizer_Error(2003, "상품 정규화에 실패했습니다"),

    ;

    private final int code;
    private final String message;

}