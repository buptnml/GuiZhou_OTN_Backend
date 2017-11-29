package com.bupt.facade.util;

public class BussinessPowerStringTransfer {
    /*考虑到设计简单的原则，输入输出功率是以字符串的形式存储在数据库的，这里需要转换
    */
    public static double[][] stringTransfer(String powerString) {
        if (null == powerString || powerString.equals("[]")) {
            return new double[0][0];
        }
        if (powerString.contains("[")) {
            //对二维矩阵转换成的字符串进行操作
            String[] powerArrays = powerString.replace("]", ">").replace("[", "<").split(">,( )*<");
            double[][] results = new double[powerArrays.length][];
            for (int i = 0; i < powerArrays.length; i++) {
                String[] powers = powerArrays[i].replace("<", "").replace(">", "").split(",( )*");
                results[i] = new double[powers.length];
                for (int j = 0; j < results[i].length; j++) {
                    results[i][j] = Double.parseDouble(powers[j]);
                }
            }
            return results;
        } else {
            double inputPower = Double.parseDouble(powerString);
            double[][] result = new double[1][1];
            result[0][0] = inputPower;
            return result;
        }

    }
}