package com.nekoimi.gunnel.contract;

import com.nekoimi.gunnel.common.utils.JsonUtils;

/**
 * nekoimi  2021/8/15 0:00
 */
public interface Jsonable {
    /**
     * to json
     * @return
     */
    default String toJson() {
        return JsonUtils.toJson(this);
    }
}
