package com.nekoimi.gunnel.common.protocol.request;

import com.nekoimi.gunnel.common.protocol.CommonInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * nekoimi  2021/8/15 12:58
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class GuLoginReq extends CommonInfo {
    private String privateKey;
    private String hostname;
    private String os;
    private String arch;
    private String user;
    private long clientId;
    private Map<String, String> metas;
}
