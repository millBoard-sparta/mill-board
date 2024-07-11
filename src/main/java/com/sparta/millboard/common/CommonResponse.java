package com.sparta.millboard.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse<T> {

    private String msg;
    private int status;
    private T result;
}
