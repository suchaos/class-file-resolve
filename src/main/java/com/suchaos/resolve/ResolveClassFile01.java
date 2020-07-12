package com.suchaos.resolve;

import com.suchaos.resolve.util.ByteUtil;
import com.suchaos.resolve.util.JVMUtil;

import java.io.IOException;

/**
 * 模仿 jclasslib 的功能 -- 解析 .class 文件的信息
 *
 * @author suchao
 * @date 2020/7/12
 */
public class ResolveClassFile01 {

    public static void main(String[] args) throws IOException {
        args = new String[]{"./target/classes/com/suchaos/resolve/ByteCodeTest01.class"};
        if (args.length < 1) {
            System.out.println("usage: classFilePath");
            System.exit(0);
        }

        byte[] bytes = ByteUtil.getBytes(args[0]);
        int start = 0;

        // magic number
        byte[] magicNumber = ByteUtil.getU4(bytes, start);
        System.out.println(JVMUtil.isJavaMagicNumber(magicNumber));
        System.out.println(ByteUtil.toHexString(magicNumber));
        start = start + 4;

        // minor version
        byte[] minorVersion = ByteUtil.getU2(bytes, start);
        start = start + 2;
        System.out.println(ByteUtil.toHexString(minorVersion));

        // major version
        byte[] majorVersion = ByteUtil.getU2(bytes, start);
        start = start + 2;
        System.out.println(ByteUtil.toHexString(majorVersion));

        // constant_pool_count
        int constantPoolCount = ByteUtil.sumByte(ByteUtil.getU2(bytes, start));
        start = start + 2;
        System.out.println("常量池数量: " + constantPoolCount);

        // constant_pool[constant_pool_count-1];
        // constant_pool[1]

        for (int i = 1; i <= constantPoolCount - 1; i++) {
            int cp = ByteUtil.sumByte(ByteUtil.getU1(bytes, start));
            //System.out.println("cp: " + cp);
            start = start + 1;
            switch (cp) {
                case 10:
                    byte[] classIndex = ByteUtil.getU2(bytes, start);
                    start = start + 2;
                    System.out.println("#" + i + "classIndex: " + ByteUtil.sumByte(classIndex));

                    byte[] nameAndTypeIndex = ByteUtil.getU2(bytes, start);
                    start = start + 2;
                    System.out.println("#" + i + " nameAndTypeIndex: " + ByteUtil.sumByte(nameAndTypeIndex));
                    break;

                case 7:
                    byte[] nameIndex = ByteUtil.getU2(bytes, start);
                    start = start + 2;
                    System.out.println("#" + i + " classinfo:  nameIndex: " + ByteUtil.sumByte(nameIndex));
                    break;

                case 1:
                    int length = ByteUtil.sumByte(ByteUtil.getU2(bytes, start));
                    start = start + 2;
                    byte[] utf8Info = ByteUtil.getU(bytes, start, length);
                    start = start + length;
                    System.out.println("#" + i + " utf8: length: " + length + ", content:" + JVMUtil.utf8Info(utf8Info));
                    break;

                case 12:
                    byte[] nameIndex1 = ByteUtil.getU2(bytes, start);
                    start = start + 2;
                    System.out.println("#" + i + "nameIndex: " + ByteUtil.sumByte(nameIndex1));

                    byte[] descriptionIndex = ByteUtil.getU2(bytes, start);
                    start = start + 2;
                    System.out.println("#" + i + "descriptionIndex: " + ByteUtil.sumByte(descriptionIndex));
            }
        }

    }
}
