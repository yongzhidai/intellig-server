package com.dyz.intellig.service.intelligserver.sockettest;

import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.InMsg;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.OutMsg;
import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * Created by daiyongzhi on 2019/8/26.
 */
public class TcpServerTest {


    public static void main(String[] args) throws Exception {
        int port = 11811;
        String host = "localhost";
        TcpConnection connection = TcpClient.getConnection(host,port);
        Scanner sc = new Scanner(System.in);

        String line = null;
        OutMsg response = null;
        while (true) {
            line = sc.nextLine();
            if (StringUtils.isEmpty(line)) {
                System.out.println("不能发送空信息!");
                continue;
            }
            if ("send".equalsIgnoreCase(line)) {
                if (response == null) {
                    System.out.println("请先输入要发送的数据");
                    continue;
                }
                connection.sendMsg(response);
                InMsg request = connection.getResponse();
                if(request.getMsgCode() == 2){
                }else if(request.getMsgCode() == 1002){
                    System.out.println("收到登录响应:"+ request.readUTF());
                }
            }else if("close".equalsIgnoreCase(line)){
                connection.close();
                System.out.println("断开连接");
            }
            String[] strs = line.split(" ");
            if (strs.length != 2) {
                continue;
            }
            if ("code".equalsIgnoreCase(strs[0])) {
                response = new OutMsg(Integer.parseInt(strs[1]));
            } else if (isNumeric(strs[0])) {
                if (response == null) {
                    System.out.println("请先输入消息码");
                    continue;
                }
                int size = Integer.parseInt(strs[0]);
                if (size == 1) {
                    response.writeByte(Byte.parseByte(strs[1]));
                } else if (size == 2) {
                    response.writeShort(Short.parseShort(strs[1]));
                } else if (size == 4) {
                    response.writeInt(Integer.parseInt(strs[1]));
                } else if (size == 8) {
                    response.writeLong(Long.parseLong(strs[1]));
                } else {
                    System.out.println("不支持的长度！");
                }
            } else if ("s".equalsIgnoreCase(strs[0])) {
                if (response == null) {
                    System.out.println("请先输入消息码");
                    continue;
                }
                response.writeUTF(strs[1]);
            }
        }
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }

}
