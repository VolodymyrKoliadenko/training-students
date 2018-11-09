/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koliadenko.hashuniformtest;

import java.io.UnsupportedEncodingException;
import static java.lang.Math.pow;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author koliadenko
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("SHA-512");//MD5" "SHA-512"
        int N = 100_000;

        ArrayList<Long> list = new ArrayList<>(N);

        for (int i = 0; i < N; i++) {
            String input = "" + i;

            byte[] hashedBytes = digest.digest(input.getBytes("UTF-8"));
            list.add(convertArrayToLong(hashedBytes));
        }

        //list.sort(Long::compare);
        double min = list.stream().mapToLong(v -> v).min().getAsLong();
        double max = list.stream().mapToLong(v -> v).max().getAsLong();
        double s = 0;
        double s2 = 0;

        for (long i : list) {
            s += i;
            s2 += pow((double) i, 2);
        }

        double n = (double) N;
        s = s / n;
        System.out.println("MAX :" + max);
        System.out.println("Среднее, относительно максимума " + s / max + " teoret: " + (max + min) / 2 / max);
        System.out.println("despercia " + (s2 / n - s * s)
                + " teoret: " + (pow(max - min + 1, 2) - 1) / 12);

        //System.out.println(list);
    }

    static Long convertArrayToLong(byte[] hashedBytes) {
        byte[] newAr = new byte[8];
        for (int i = 0; i < 8; i++) {
            newAr[i] = hashedBytes[i];
        }
        //newAr = Arrays.copyOfRange(hashedBytes, 0, 63);
        //System.out.println(convertByteArrayToHexString(newAr));
        return (new BigInteger(newAr)).longValue();
    }

    //ориганальная версия с интеа
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
