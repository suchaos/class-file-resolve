package com.suchaos.resolve.structure.cp;

import lombok.Data;

/**
 * ConstantMethodRefInfo
 *
 * @author suchao
 * @date 2020/7/12
 */
@Data
public class ConstantMethodRefInfo implements CpInfo {

    public static final byte tag =  10; // u1

    int index; // 在常量池中的位置，从 1 开始

    int classIndex; // u2

    int nameAndTypeIndex; // u2

    @Override
    public String getTag() {
        return tag + " ConstantMethodRefInfo";
    }
}
