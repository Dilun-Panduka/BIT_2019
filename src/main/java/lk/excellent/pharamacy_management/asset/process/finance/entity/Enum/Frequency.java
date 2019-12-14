package lk.excellent.pharamacy_management.asset.process.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Frequency {
    BD("2"),
    TDS("3"),
    OD("1"),
    OM("1"),
    ON("1"),
    PRN("0"),
    QDS("4"),
    QQH("6"),
    TID("3"),
    FIVE_TIMES("5");

    private final String frequency;

}
