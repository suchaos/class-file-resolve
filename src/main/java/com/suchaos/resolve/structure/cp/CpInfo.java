package com.suchaos.resolve.structure.cp;

/**
 * 常量池通用格式
 *
 * @author suchao
 * @date 2020/7/12
 */
public interface CpInfo {

    /**
     * getTag
     * @return 常量池中每一项的具体类型
     */
    default String getTag() {
        return this.getClass().getSimpleName();
    }
}
