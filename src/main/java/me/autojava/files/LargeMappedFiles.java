package me.autojava.files;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by shilong.zhang on 2018/1/26.
 */
public class LargeMappedFiles {
    static int length = 0x8000000;

    public static void main(String[] args) throws IOException {
        FileChannel fc = new RandomAccessFile("test.dat", "rw").getChannel();

        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);

        for (int i = 0; i < length; i++) {
            out.put((byte) 'x');
        }
        System.out.println("Finised writing");

        for (int i = length/2; i < length/2 + 6; i++) {
            System.out.println((char) out.get(i));
        }

        fc.close();
    }

}
