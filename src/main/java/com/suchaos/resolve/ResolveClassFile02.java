package com.suchaos.resolve;

import com.suchaos.resolve.structure.ClassFileInfo;
import com.suchaos.resolve.util.ByteUtil;
import com.suchaos.resolve.util.JVMUtil;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模仿 jclasslib 的功能 -- 解析 .class 文件的信息
 *
 * @author suchao
 * @date 2020/7/12
 */
public class ResolveClassFile02 {

    public static void main(String[] args) throws IOException {
        args = new String[]{"./target/classes/com/suchaos/resolve/ByteCodeTest01.class"};
        if (args.length < 1) {
            System.out.println("usage: classFilePath");
            System.exit(0);
        }

        byte[] bytes = ByteUtil.getBytes(args[0]);
        System.out.println("字节码长度: " + bytes.length);
        AtomicInteger index = new AtomicInteger(0);

        // Basic Info --> {magic number, minor version, major version, constant_pool_count}
        ClassFileInfo classFileInfo = new ClassFileInfo();
        classFileInfo.setBasicInfo(JVMUtil.resolveBasicInfo(bytes, index));
        System.out.println(classFileInfo);
        System.out.println(index.get());

        // Constant_pool
        int constantPoolCount = classFileInfo.getBasicInfo().getConstantPoolCount();
        classFileInfo.setConstantPool(
                JVMUtil.resolveConstantPoolInfo(bytes, index, constantPoolCount));

        classFileInfo.getConstantPool().forEach(System.out::println);
        System.out.println(index.get());
    }
}
