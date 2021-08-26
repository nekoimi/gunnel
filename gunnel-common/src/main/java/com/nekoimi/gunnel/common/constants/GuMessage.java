package com.nekoimi.gunnel.common.constants;

import com.nekoimi.gunnel.common.utils.JsonUtils;

/**
 * nekoimi  2021/8/26 11:14
 *
 * 使用枚举来实现
 */
public interface GuMessage {
    /**
     * 获取消息类型
     *
     * @return
     */
    Class<?> getType();

    /**
     * 解析消息数据
     *
     * @param json
     * @return
     */
    default Object parse(String json) {
        return JsonUtils.parse(json, getType());
    }

    /**
     * 消息json化
     *
     * @return
     */
    default String toJson() {
        return JsonUtils.toJson(this);
    }
}
