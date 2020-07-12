package com.suchaos.resolve.util;

import com.suchaos.resolve.structure.BasicInfo;
import com.suchaos.resolve.structure.cp.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JVMUtil
 *
 * @author suchao
 * @date 2020/7/12
 */
public class JVMUtil {

    public static byte[] javaMagicNumber =
            new byte[]{(byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte) 0xbe};

    public static boolean isJavaMagicNumber(byte[] bytes) {
        return ByteUtil.contentEquals(bytes, javaMagicNumber);
    }

    public static String utf8Info(byte[] bytes) {
        // TODO: java 虚拟机规范 P70, 其实分为好几种情况
        return new String(bytes);
    }

    public static BasicInfo resolveBasicInfo(byte[] bytes, AtomicInteger index) {
        BasicInfo basicInfo = new BasicInfo();

        // magic number
        byte[] magicNumber = ByteUtil.getU4(bytes, index.getAndAdd(4));

        if (isJavaMagicNumber(magicNumber)) {
            basicInfo.setMagicNumber(ByteUtil.toHexString(magicNumber));

            // minor version
            byte[] minorVersion = ByteUtil.getU2(bytes, index.getAndAdd(2));
            basicInfo.setMinorVersion(ByteUtil.toHexString(minorVersion));

            // major version
            byte[] majorVersion = ByteUtil.getU2(bytes, index.getAndAdd(2));
            basicInfo.setMajorVersion(ByteUtil.toHexString(majorVersion));

            // constant_pool_count
            int constantPoolCount = ByteUtil.sumByte(ByteUtil.getU2(bytes, index.getAndAdd(2)));
            basicInfo.setConstantPoolCount(constantPoolCount);
        } else {
            throw new RuntimeException("非法的 class 文件，魔数不对");
        }
        return basicInfo;
    }

    public static List<CpInfo> resolveConstantPoolInfo(byte[] bytes, AtomicInteger index, int constantPoolCount) {
        List<CpInfo> list = new ArrayList<>();

        for (int i = 1; i <= constantPoolCount - 1; i++) {
            int cpType = ByteUtil.sumByte(ByteUtil.getU1(bytes, index.getAndAdd(1)));

            CpInfo cpInfo = getCpInfoByType(bytes, index, i, cpType);
            list.add(Objects.requireNonNull(cpInfo, "第 " + i + " 个常量返回 null"));
        }

        return list;
    }

    /**
     * @param bytes  字节码
     * @param index  解析到的字节码中的位置
     * @param i      常量池中的位置
     * @param cpType 常量池的 tag 类型
     */
    private static CpInfo getCpInfoByType(byte[] bytes, AtomicInteger index, int i, int cpType) {
        // System.out.println("第" + i + "个, cpType = " + cpType);
        switch (cpType) {
            case 10: // ConstantMethodRefInfo
                byte[] methodRefClassIndex = ByteUtil.getU2(bytes, index.getAndAdd(2));
                byte[] methodRefNameAndTypeIndex = ByteUtil.getU2(bytes, index.getAndAdd(2));

                ConstantMethodRefInfo constantMethodRefInfo = new ConstantMethodRefInfo();
                constantMethodRefInfo.setIndex(i);
                constantMethodRefInfo.setClassIndex(ByteUtil.sumByte(methodRefClassIndex));
                constantMethodRefInfo.setNameAndTypeIndex(ByteUtil.sumByte(methodRefNameAndTypeIndex));
                return constantMethodRefInfo;

            case 7: // ConstantClassInfo
                byte[] classInfoNameIndex = ByteUtil.getU2(bytes, index.getAndAdd(2));
                // System.out.println("#" + i + " classinfo:  classInfoNameIndex: " + ByteUtil.sumByte(classInfoNameIndex));

                ConstantClassInfo constantClassInfo = new ConstantClassInfo();
                constantClassInfo.setIndex(i);
                constantClassInfo.setNameIndex(ByteUtil.sumByte(classInfoNameIndex));
                return constantClassInfo;

            case 1: // ConstantUtf8Info
                int length = ByteUtil.sumByte(ByteUtil.getU2(bytes, index.getAndAdd(2)));
                byte[] utf8Info = ByteUtil.getU(bytes, index.getAndAdd(length), length);
                // System.out.println("#" + i + " utf8: length: " + length + ", content:" + JVMUtil.utf8Info(utf8Info));

                ConstantUtf8Info constantUtf8Info = new ConstantUtf8Info();
                constantUtf8Info.setIndex(i);
                constantUtf8Info.setLength(length);
                constantUtf8Info.setBytes(JVMUtil.utf8Info(utf8Info));
                return constantUtf8Info;

            case 12: // ConstantNameAndTypeInfo
                byte[] nameAndTypeNameIndex = ByteUtil.getU2(bytes, index.getAndAdd(2));
                // System.out.println("#" + i + "classInfoNameIndex: " + ByteUtil.sumByte(nameAndTypeNameIndex));

                byte[] nameAndTypeDescriptionIndex = ByteUtil.getU2(bytes, index.getAndAdd(2));
                // System.out.println("#" + i + "nameAndTypeDescriptionIndex: " + ByteUtil.sumByte(nameAndTypeDescriptionIndex));

                ConstantNameAndTypeInfo constantNameAndTypeInfo = new ConstantNameAndTypeInfo();
                constantNameAndTypeInfo.setIndex(i);
                constantNameAndTypeInfo.setNameIndex(ByteUtil.sumByte(nameAndTypeNameIndex));
                constantNameAndTypeInfo.setDescriptorIndex(ByteUtil.sumByte(nameAndTypeDescriptionIndex));
                return constantNameAndTypeInfo;

            default:
                break;
        }
        return null;
    }
}
