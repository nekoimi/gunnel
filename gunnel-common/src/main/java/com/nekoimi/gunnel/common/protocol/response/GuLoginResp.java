package com.nekoimi.gunnel.common.protocol.response;

import com.nekoimi.gunnel.common.protocol.CommonInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * nekoimi  2021/8/26 10:03
 */
@Data
@NoArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class GuLoginResp extends CommonInfo {
}
