package com.example.tcc2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FreezeStatusEnum {

    UNDO(0, "未处理"),
    COMMIT_STATUS(1, "commit已处理"),
    CANCEL_STATUS(2, "cancel已处理");

    private Integer status;

    private String desc;
}
