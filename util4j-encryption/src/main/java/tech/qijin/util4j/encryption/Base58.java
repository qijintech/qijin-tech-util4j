package tech.qijin.util4j.encryption;

import javax.xml.bind.DatatypeConverter;

public class Base58 {

    public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char ENCODED_ZERO = ALPHABET[0];

    public Base58() {

    }

    /**
     * return string encode using base58
     * @param str word
     * @return encoded word
     */
    public String encode(String str) {

        // getByte
        byte [] b = str.getBytes();

        // string → Hex
        String s_hex = DatatypeConverter.printHexBinary(b);

        // Hex → Decimal
        long decimal = Long.valueOf(s_hex, 16);

        // Decimal → 58 kinds of characters
        StringBuffer res = new StringBuffer();
        while (decimal > 0) {
            char c = ALPHABET[(int)decimal%58];
            res.append(c);
            decimal = decimal/58;
        }

        // zero byte confirm
        byte [] temp_b = str.getBytes();
        for (int i = 0; i < temp_b.length; i++) {
            if (temp_b[i] != 0) {
                break;
            }
            res.append(ENCODED_ZERO);
        }

        return res.reverse().toString();
    }

    public String encode(Long value) {

        // Hex → Decimal
        long decimal = value;

        // Decimal → 58 kinds of characters
        StringBuffer res = new StringBuffer();
        while (decimal > 0) {
            char c = ALPHABET[(int)decimal%58];
            res.append(c);
            decimal = decimal/58;
        }

        // zero byte confirm
//        byte [] temp_b = str.getBytes();
//        for (int i = 0; i < temp_b.length; i++) {
//            if (temp_b[i] != 0) {
//                break;
//            }
//            res.append(ENCODED_ZERO);
//        }

        return res.reverse().toString();
    }

}
