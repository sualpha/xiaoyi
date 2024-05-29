package com.cloudyi.member.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

public interface MemberRedeemCodeEnums {

    @AllArgsConstructor
    public enum MemberRedeemCodeUnitEnum {

        MONTH(1,"月"),
        YEAR(2,"年"),
        ;

        @Getter
        private final Integer code;

        @Getter
        private final String name;

    }


    @AllArgsConstructor
    public enum MemberRedeemCodeStatusEnum {

        NOT_USED(0,"未使用"),
        USED(1,"已使用"),
        ;

        @Getter
        private final Integer code;

        @Getter
        private final String name;

    }

}
