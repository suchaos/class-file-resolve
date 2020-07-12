package com.suchaos.resolve.structure.cp;

import lombok.Data;

/**
 * ConstantNameAndTypeInfo
 *
 * @author suchao
 * @date 2020/7/12
 */
@Data
public class ConstantNameAndTypeInfo implements CpInfo {

    public static final byte tag = 12; // u1

    int index; // 在常量池中的位置，从 1 开始

    int nameIndex; // u2

    int descriptorIndex; // u2

    @Override
    public String getTag() {
        return tag + " ConstantNameAndTypeInfo";
    }
}
