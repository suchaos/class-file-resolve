package com.suchaos.resolve.structure.cp;

import lombok.Data;

/**
 * ConstantClassInfo
 *
 * @author suchao
 * @date 2020/7/12
 */
@Data
public class ConstantClassInfo implements CpInfo {

    public static final byte tag = 7;

    int index; // 在常量池中的位置，从 1 开始

    int nameIndex; // u2

    @Override
    public String getTag() {
        return tag + " ConstantClassInfo";
    }
}
