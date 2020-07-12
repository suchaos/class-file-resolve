package com.suchaos.resolve.structure;

import com.suchaos.resolve.structure.cp.CpInfo;
import lombok.Data;

import java.util.List;

/**
 * class 文件总信息汇总
 *
 * @author suchao
 * @date 2020/7/12
 */
@Data
public class ClassFileInfo {

    private BasicInfo basicInfo;

    private List<CpInfo> constantPool;
}
