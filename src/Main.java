import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        Main main = new Main();
        main.b("1608029629.jdapkg");
        main.a();
//        StreamA a = main.a("app-service.js");
        StreamA a = main.a("app-config.json");
        byte[] dst = new byte[a.size()];
        a.read(dst, 0, a.size());
        String s = new String(dst);
    }

    private volatile int e;
    private volatile FileChannel fileChannel;
    private volatile int g;
    private volatile int h;
    public static final ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

    private boolean b(String filePaht) {

        if (this.fileChannel == null) {
            try {
                this.fileChannel = new RandomAccessFile(filePaht, "r").getChannel();
            } catch (Throwable th) {
            }
        }
        if (this.fileChannel == null) {
            return false;
        }
        try {
            this.fileChannel.position(0L);
            ByteBuffer allocate = ByteBuffer.allocate(14);
            allocate.order(byteOrder);
            this.fileChannel.read(allocate);
            if (-66 == allocate.get(0)) {
                if (-19 == allocate.get(13)) {
                    byte[] array = allocate.array();
                    this.e = b.a(array, 1);
                    this.g = b.a(array, 5);
                    this.h = b.a(array, 9);
                    return true;
                }
            }
            return false;
        } catch (Throwable th2) {
            return false;
        }
    }

    public final class a {
        public String name;
        public int start;
        public int end;

        public a(String str, int i, int i2) {
            this.name = str;
            this.start = i;
            this.end = i2;
        }
    }

    public volatile Map<String, a> stringMap;
    private volatile int i;

    // page-frame.html
    public final boolean a() {

        if (this.stringMap != null && this.i >= 0 && this.i == this.stringMap.size()) {
            return true;
        } else {
            try {
                this.fileChannel.position(14L);
                ByteBuffer allocate = ByteBuffer.allocate(this.g);
                allocate.order(byteOrder);
                this.fileChannel.read(allocate);
                byte[] array = allocate.array();
                this.i = b.a(array, 0);
                HashMap hashMap = new HashMap();
                int i2 = 0;
                int i3 = 4;
                while (i2 < this.i) {
                    int a2 = b.a(array, i3);
                    int i4 = i3 + 4;
                    String str = new String(array, i4, a2);
                    int i5 = i4 + a2;
                    int a3 = b.a(array, i5);
                    int i6 = i5 + 4;
                    int a4 = b.a(array, i6);
                    i3 = i6 + 4;
                    a aVar2 = new a(str, a3, a4);
                    System.out.println("start: " + a3 + " len: " + a4);
                    hashMap.put(str, aVar2);
                    System.out.println("name: "+str);
                    i2++;
                }
                this.stringMap = hashMap;
                return true;
            } catch (Throwable th) {
                return false;
            }
        }
    }

    public final StreamA a(String str) {
        if (this.stringMap == null || (str == null || str == "")) {
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(this.stringMap == null ? 0 : this.stringMap.size());
            objArr[1] = str;
            return null;
        } else {
            a aVar = this.stringMap.get(b.a(str));
            if (aVar == null) {
                return null;
            }
            try {
                MappedByteBuffer map = this.fileChannel.map(FileChannel.MapMode.READ_ONLY, aVar.start, aVar.end);
                map.order(byteOrder);
                map.limit(aVar.end);
                return new StreamA(map);
            } catch (Throwable th) {
                return null;
            }
        }
    }

}
