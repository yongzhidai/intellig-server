package com.dyz.intellig.service.intelligserver.tcp.msg.transfer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
public class ClientRequest {
    private int msgCode;
    private DataInputStream dis;

    public ClientRequest(byte[] bytes, int offset, int length) throws IOException {
        dis = new DataInputStream(new ByteArrayInputStream(bytes,offset,length));
        this.msgCode = dis.readInt();
    }

    public int getMsgCode() {
        return msgCode;
    }

    public String readUTF() throws IOException {
        return dis.readUTF();
    }
}
