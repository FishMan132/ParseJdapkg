import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class b {
    static int a(byte[] bArr, int i) {
        if (bArr == null) {
            return 0;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr, i, 4);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return wrap.getInt();
    }

    public static String a(String str) {
        if (str == null || str == "") {
            return str;
        }
        int i = 0;
        while (i < str.length() && '/' == str.charAt(i)) {
            i++;
        }
        return str.substring(i);
    }
}