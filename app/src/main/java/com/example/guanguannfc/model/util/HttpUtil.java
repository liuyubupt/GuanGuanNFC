package com.example.guanguannfc.model.util;

import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
    private static String urls = "http://39.97.66.19:8070/file/getImg";
    private static String urls_1 = "http://39.97.66.19:8070/file/upload";
    public static String uploadFile(File file, String imgName) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "---------------------------823928434";
        try {
            URL url = new URL(urls_1);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"file1\"; filename="+imgName+".jpg" + end);
            dos.writeBytes(end);

            byte[] bytes = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while (inputStream.read(bytes) != -1) {
                dos.write(bytes);
            }
            inputStream.close();
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            dos.close();
            // 读取服务器返回结果
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            is.close();
            if(result != null) return  result;
            else return "NullPointer";

        } catch (IOException e) {
            e.printStackTrace();
            return "Exception";
        }

    }

    public static Drawable getImg(String imgName) {
        try {
            String params = "imgName="+imgName;
            // 1. 获取访问地址URL
            URL url = new URL(urls);
            // 2. 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数等 */
            // 请求方式
            connection.setRequestMethod("POST");
            // 超时时间
            connection.setConnectTimeout(5000);
            // 设置是否输出
            connection.setDoOutput(true);
            // 设置是否读入
            connection.setDoInput(true);
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置使用标准编码格式编码参数的名-值对
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 连接(有问题)
//            connection.connect();
            /* 4. 处理输入输出 */
            // 写入参数到请求中
            OutputStream out = connection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();
            // 从连接中读取响应信息
            InputStream inputStream = connection.getInputStream();
            String msg = "";
            int code = connection.getResponseCode();
            if (code == 200) {
                return loadImageFromNetwork(inputStream);

            }
            // 5. 断开连接
            connection.disconnect();

            // 处理结果
            System.out.println(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    private static Drawable loadImageFromNetwork(InputStream inputStream) {

        Drawable drawable = Drawable.createFromStream(inputStream, null);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;

    }


    public static String get(String urls, String params) {
        try {
            // 1. 获取访问地址URL
            URL url = new URL(urls + params);
            // 2. 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数等 */
            // 超时时间
            connection.setConnectTimeout(200);

            // 设置是否读入
            connection.setDoInput(true);
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);

            // 设置使用标准编码格式编码参数的名-值对
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 连接(有问题)
//            connection.connect();

            int code = connection.getResponseCode();
            if (code == 200) {

                // 从连接中读取响应信息

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null)stringBuffer.append(temp);
                return stringBuffer.toString();

            }
            // 5. 断开连接
            connection.disconnect();

            // 处理结果

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "网络故障";

    }



    public static String post(String urls, String params) {

        try {
            // 1. 获取访问地址URL
            URL url = new URL(urls);
            // 2. 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数等 */
            // 请求方式
            connection.setRequestMethod("POST");
            // 超时时间
            connection.setConnectTimeout(100);
            // 设置是否输出
            connection.setDoOutput(true);
            // 设置是否读入
            connection.setDoInput(true);
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置使用标准编码格式编码参数的名-值对
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 连接(有问题)
//            connection.connect();
            /* 4. 处理输入输出 */
            // 写入参数到请求中
            OutputStream out = connection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();
            // 从连接中读取响应信息
            String msg = "";
            int code = connection.getResponseCode();
            if (code == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    msg += line;
                }
                reader.close();
                return msg;
            }
            // 5. 断开连接
            connection.disconnect();

            // 处理结果
            System.out.println(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "网络故障";

    }


}
