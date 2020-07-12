package com.suchaos.resolve.structure.cp;

import lombok.Data;

/**
 * ConstantUtf8Info
 *
 * @author suchao
 * @date 2020/7/12
 */
@Data
public class ConstantUtf8Info implements CpInfo {

    public static final byte tag = 1; // u1

    int index; // 在常量池中的位置，从 1 开始

    int length; // u2

    String bytes; // u-length

    @Override
    public String getTag() {
        return tag + " ConstantUtf8Info";
    }
}
