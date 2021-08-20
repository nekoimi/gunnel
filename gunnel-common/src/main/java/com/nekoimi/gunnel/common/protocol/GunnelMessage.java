package com.nekoimi.gunnel.common.protocol;

import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * nekoimi  2021/8/14 18:24
 */
@Data
@AllArgsConstructor(staticName = "of")
public class GunnelMessage {
    private EMessage type;
    private Object message;

    public String toJsonMessage() {
        return JsonUtils.toJson(message);
    }
}
