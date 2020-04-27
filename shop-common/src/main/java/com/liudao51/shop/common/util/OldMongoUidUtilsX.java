package com.liudao51.shop.common.util;

import com.google.common.base.Objects;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MongoDB的ObjectId对象的全局唯一标识符。
 * 由12个字节组成，划分如下：
 * 1）4字节（8位）timeStamp：UNIX时间戳（精确到秒)。
 * 2）3字节（6位）machine：所在主机的唯一标识符（一般是机器主机名的散列值）。
 * 3）2字节（4位）pid：同一台机器不同的进程产生objectid的进程标识符 。
 * 4）3字节（6位）increment：由一个随机数开始的计数器生成的自动增加的值，用来确保在同一秒内产生的objectid也不会发现冲突，允许256^3（16777216）条记录的唯一性。
 * <p>
 * 2次生成的结果为：
 * 5dbbd4c3 d2c335 d03a c768fd
 * 5dbbd555 d2c3b5 315c 045c13
 */
public class OldMongoUidUtilsX implements Comparable<OldMongoUidUtilsX>, java.io.Serializable {

    private final int _time;
    private final int _machine;
    private final int _inc;
    private boolean _new;
    private static final int _genmachine;

    private static AtomicInteger _nextInc = new AtomicInteger((new Random()).nextInt());

    private static final long serialVersionUID = -4415279469780082174L;

    private static final Logger LOGGER = Logger.getLogger("org.bson.ObjectId");

    /**
     * Create a new object id.
     */
    public OldMongoUidUtilsX() {
        _time = (int) (System.currentTimeMillis() / 1000);
        _machine = _genmachine;
        _inc = _nextInc.getAndIncrement();
        _new = true;
    }

    public static String id() {
        return get().toHexString();
    }

    /**
     * Gets a new object id.
     *
     * @return the new id
     */
    public static OldMongoUidUtilsX get() {
        return new OldMongoUidUtilsX();
    }

    /**
     * Checks if a string could be an {@code ObjectId}.
     *
     * @param s a potential ObjectId as a String.
     * @return whether the string could be an object id
     * @throws IllegalArgumentException if hexString is null
     */
    public static boolean isValid(String s) {
        if (s == null)
            return false;

        final int len = s.length();
        if (len != 24)
            return false;

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
                continue;
            if (c >= 'a' && c <= 'f')
                continue;
            if (c >= 'A' && c <= 'F')
                continue;

            return false;
        }

        return true;
    }


    /**
     * Converts this instance into a 24-byte hexadecimal string representation.
     *
     * @return a string representation of the ObjectId in hexadecimal format
     */
    public String toHexString() {
        final StringBuilder buf = new StringBuilder(24);
        for (final byte b : toByteArray()) {
            buf.append(String.format("%02x", b & 0xff));
        }
        return buf.toString();
    }

    /**
     * Convert to a byte array.  Note that the numbers are stored in big-endian order.
     *
     * @return the byte array
     */
    public byte[] toByteArray() {
        byte b[] = new byte[12];
        ByteBuffer bb = ByteBuffer.wrap(b);
        // by default BB is big endian like we need
        bb.putInt(_time);
        bb.putInt(_machine);
        bb.putInt(_inc);
        return b;
    }

    private int _compareUnsigned(int i, int j) {
        long li = 0xFFFFFFFFL;
        li = i & li;
        long lj = 0xFFFFFFFFL;
        lj = j & lj;
        long diff = li - lj;
        if (diff < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        if (diff > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return (int) diff;
    }

    public int compareTo(OldMongoUidUtilsX id) {
        if (id == null)
            return -1;

        int x = _compareUnsigned(_time, id._time);
        if (x != 0)
            return x;

        x = _compareUnsigned(_machine, id._machine);
        if (x != 0)
            return x;

        return _compareUnsigned(_inc, id._inc);
    }

    /**
     * Gets the timestamp (number of seconds since the Unix epoch).
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return _time;
    }

    /**
     * Gets the timestamp as a {@code Date} instance.
     *
     * @return the Date
     */
    public Date getDate() {
        return new Date(_time * 1000L);
    }


    /**
     * Gets the current value of the auto-incrementing counter.
     *
     * @return the current counter value.
     */
    public static int getCurrentCounter() {
        return _nextInc.get();
    }


    static {

        try {
            // build a 2-byte machine piece based on NICs info
            int machinePiece;
            {
                try {
                    StringBuilder sb = new StringBuilder();
                    Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
                    while (e.hasMoreElements()) {
                        NetworkInterface ni = e.nextElement();
                        sb.append(ni.toString());
                    }
                    machinePiece = sb.toString().hashCode() << 16;
                } catch (Throwable e) {
                    // exception sometimes happens with IBM JVM, use random
                    LOGGER.log(Level.WARNING, e.getMessage(), e);
                    machinePiece = (new Random().nextInt()) << 16;
                }
                LOGGER.fine("machine piece post: " + Integer.toHexString(machinePiece));
            }

            // add a 2 byte process piece. It must represent not only the JVM but the class loader.
            // Since static var belong to class loader there could be collisions otherwise
            final int processPiece;
            {
                int processId = new Random().nextInt();
                try {
                    processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
                } catch (Throwable t) {
                }

                ClassLoader loader = OldMongoUidUtilsX.class.getClassLoader();
                int loaderId = loader != null ? System.identityHashCode(loader) : 0;

                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toHexString(processId));
                sb.append(Integer.toHexString(loaderId));
                processPiece = sb.toString().hashCode() & 0xFFFF;
                LOGGER.fine("process piece: " + Integer.toHexString(processPiece));
            }

            _genmachine = machinePiece | processPiece;
            LOGGER.fine("machine : " + Integer.toHexString(_genmachine));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OldMongoUidUtilsX that = (OldMongoUidUtilsX) o;

        return Objects.equal(this.serialVersionUID, that.serialVersionUID) &&
                Objects.equal(this.LOGGER, that.LOGGER) &&
                Objects.equal(this._time, that._time) &&
                Objects.equal(this._machine, that._machine) &&
                Objects.equal(this._inc, that._inc) &&
                Objects.equal(this._new, that._new) &&
                Objects.equal(this._nextInc, that._nextInc) &&
                Objects.equal(this._genmachine, that._genmachine);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialVersionUID, LOGGER, _time, _machine, _inc, _new,
                _nextInc, _genmachine);
    }

    public static void main(String[] args) {
        System.out.println(new OldMongoUidUtilsX().toHexString());
        System.out.println(new OldMongoUidUtilsX().toHexString());
        System.out.println(new OldMongoUidUtilsX().toHexString());
    }
}