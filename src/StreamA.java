import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class StreamA extends InputStream {

    public final ByteBuffer f6746a;
    private int f6747b;

    public int size(){
        return this.f6746a.limit();
    }

    public StreamA(ByteBuffer byteBuffer) {
        this.f6746a = byteBuffer;
    }

    public static void a(ByteBuffer byteBuffer) {
        if (byteBuffer.getClass().getName().equals("java.nio.DirectByteBuffer")) {
            try {
                Method declaredMethod = byteBuffer.getClass().getDeclaredMethod("free", new Class[0]);
                boolean isAccessible = declaredMethod.isAccessible();
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(byteBuffer, new Object[0]);
                declaredMethod.setAccessible(isAccessible);
            } catch (Exception unused) {
            }
        }
        System.gc();
    }

    @Override // java.io.InputStream
    public final int available() {
        return this.f6746a.remaining();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.io.InputStream
    public final void close() throws IOException {
        super.close();
        a(this.f6746a);
    }

    public final synchronized void mark(int i) {
        this.f6747b = this.f6746a.position();
    }

    public final boolean markSupported() {
        return true;
    }

    @Override // java.io.InputStream
    public final int read() {
        if (this.f6746a.hasRemaining()) {
            return this.f6746a.get() & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        if (!this.f6746a.hasRemaining()) {
            return -1;
        }
        int min = Math.min(i2, this.f6746a.remaining());
        this.f6746a.get(bArr, i, min);
        return min;
    }

    @Override // java.io.InputStream
    public final synchronized void reset() {
        this.f6746a.position(this.f6747b);
    }
}