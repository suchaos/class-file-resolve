package com.suchaos.resolve.util;

import com.google.common.hash.HashCode;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * ByteUtil
 *
 * @author suchao
 * @date 2020/7/12
 */
public class ByteUtil {

    /************************* getBytes ***********************************/

    public static byte[] getBytes(String fileName) {
        return getBytes(Paths.get(fileName));
    }

    public static byte[] getBytes(Path path) {
        return getBytes(path.toFile());
    }

    public static byte[] getBytes(File file) {
        try {
            return Files.asByteSource(file).read();
        } catch (IOException e) {
            System.err.println("getBytes 失败: " + e.getMessage());
        }
        return null;
    }

    /************************* getU1/2/4 ***********************************/

    public static byte[] getU(byte[] source, int start, int length) {
        return Arrays.copyOfRange(source, start, start + length);
    }

    public static byte[] getU1(byte[] source, int start) {
        return Arrays.copyOfRange(source, start, start + 1);
    }

    public static byte getU1AsByte(byte[] source, int start) {
        return source[start];
    }

    public static byte[] getU2(byte[] source, int start) {
        return Arrays.copyOfRange(source, start, start + 2);
    }

    public static byte[] getU4(byte[] source, int start) {
        return Arrays.copyOfRange(source, start, start + 4);
    }

    /************************* contentEquals ***********************************/

    public static boolean contentEquals(byte[] source, byte[] target) {
        return Arrays.equals(source, target);
    }

    /************************* print byte[] as hex string ***********************************/

    public static String toHexString(byte[] bytes) {
        // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
        return BaseEncoding.base16().lowerCase().encode(bytes);
        // return HashCode.fromBytes(bytes).toString();
    }

    public static String toString(byte[] bytes) {
        return new String(bytes);
    }

    public static int sumByte(byte[] bytes) {
        int result = 0;
        for (byte b : bytes) {
            result = result + b;
        }
        return result;
    }
}
