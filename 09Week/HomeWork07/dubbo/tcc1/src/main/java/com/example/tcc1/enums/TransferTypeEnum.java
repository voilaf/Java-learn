package com.example.tcc1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransferTypeEnum {

    DOLLAR_TO_RMB(1, "美元转人民币"),
    RMB_TO_DOLLAR(2, "人民币转美元");

    private Integer type;

    private String desc;

    public static TransferTypeEnum findEnumByType(Integer type) {
        if (type == null) {
            return null;
        }

        for (TransferTypeEnum transferTypeEnum : TransferTypeEnum.values()) {
            if (transferTypeEnum.getType().equals(type)) {
                return transferTypeEnum;
            }
        }
        return null;
    }
}
