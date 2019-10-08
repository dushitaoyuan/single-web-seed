package com.taoyuanx.codegen.domain;

import lombok.Data;

/**
 * @author dushitaoyuan
 * @date 2019/10/610:28
 * @desc: 生成配置
 */
@Data
public class GenConfig {
    private String configKey;
    private String configValue;
    private Integer type;


}
