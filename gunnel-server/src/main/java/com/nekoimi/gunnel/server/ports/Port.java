package com.nekoimi.gunnel.server.ports;

import com.nekoimi.gunnel.common.enums.EProtocol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * nekoimi  2021/8/25 16:13
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class Port {
    private EProtocol protocol;
    private int value;
}
