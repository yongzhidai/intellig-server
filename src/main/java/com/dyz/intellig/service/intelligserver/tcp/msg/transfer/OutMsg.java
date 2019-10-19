package com.dyz.intellig.service.intelligserver.tcp.msg.transfer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
public class OutMsg {


    private ByteArrayOutputStream bros;
    private DataOutputStream dos;

    public OutMsg(int msgCode) throws IOException {

        bros = new ByteArrayOutputStream();
        dos = new DataOutputStream(bros);
        dos.writeInt(msgCode);
    }

    public void writeByte(byte data) throws IOException {
        dos.write(data);
    }

    public void writeShort(short data) throws IOException {
        dos.writeShort(data);
    }

    public void writeInt(int data) throws IOException {
        dos.writeInt(data);
    }

    public void writeLong(long data) throws IOException {
        dos.writeLong(data);
    }

    public void writeUTF(String data) throws IOException {
        dos.writeUTF(data);
    }

    public byte[] toBytes(){
        return bros.toByteArray();
    }

}
