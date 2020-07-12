package com.suchaos.resolve.structure;

import lombok.Data;

/**
 * 模拟 jclasslib 中的第一项：General Information
 *
 * @author suchao
 * @date 2020/7/12
 */
@Data
public class BasicInfo {

    private String magicNumber;

    private String minorVersion;

    private String majorVersion;

    private int constantPoolCount;
}
