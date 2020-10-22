package com.example.dialogmachine;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class HttpRequest {
    /**
     * 用于Main里面的 多线程 请求消息
     * @param message
     * @return 返回ChatMessage
     */
    public static ChatMessage sendMessage(String message){
        ChatMessage chatMessage = new ChatMessage();
        String gsonResult = doGet(message);//连接请求的内容
        Gson gson = new Gson();
        Result result = null;
        if (gsonResult != null){
            try{
                result = gson.fromJson(gsonResult, Result.class);//从json数据（json格式字符串）转为java对象（解析数据
                chatMessage.setMessage(result.getText());//http连接获取的内容解析之后的结果给聊天信息赋值
            }catch (Exception e){
                chatMessage.setMessage("请求错误");
            }
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOUNT);
        return chatMessage;
    }

    /**
     * 用于封装Get请求
     * @param message
     * @return json格式String
     */
    public static String doGet(String message){
        String result = "";
        String url = setParmat(message);
        System.out.println("-----url= "+ url);
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            //设置connection参数
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("GET");
            // 调用HttpURLConnection连接对象的getInputStream()函数,
            // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
            inputStream = connection.getInputStream();//真正的发送
            byteArrayOutputStream = new ByteArrayOutputStream();
            int len = -1;
            //将其缓存到byteArrayOutputStream中
            //先申请buff缓存区
            byte[] buff = new byte[1024];
            while ((len = inputStream.read(buff)) != -1){
                byteArrayOutputStream.write(buff, 0, len);
            }
            byteArrayOutputStream.flush();
            result = new String(byteArrayOutputStream.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 拼接url
     * @param msg
     * @return url
     */
    private static String setParmat(String msg){
        String url="";
        try {
            url = MyRobot.URL_KEY + "?key=" + MyRobot.API_KEY + "&info="
                    + URLEncoder.encode(msg, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
}
