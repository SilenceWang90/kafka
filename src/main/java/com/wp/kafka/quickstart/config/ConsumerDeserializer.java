package com.wp.kafka.quickstart.config;

import com.wp.kafka.quickstart.User;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @Description 反序列化：泛型为消息的类类型，将byte[]转为泛型类型
 * @Author admin
 * @Date 2023/7/31 13:18
 */
public class ConsumerDeserializer implements Deserializer<User> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public User deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        if (data.length < 8) {
            throw new SerializationException("size is wrong, data.length must be >= 8");
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        // 获取idBytes字节数组的长度，并获取id的信息
        int idLen = byteBuffer.getInt();
        byte[] idBytes = new byte[idLen];
        byteBuffer.get(idBytes);
        // 获取nameBytes字节数组的长度，并获取name的信息
        int nameLen = byteBuffer.getInt();
        byte[] nameBytes = new byte[nameLen];
        byteBuffer.get(nameBytes);
        // 创建User对象
        String id, name;
        try {
            id = new String(idBytes, "UTF-8");
            name = new String(nameBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("deserializing error! ", e);
        }
        return new User(id, name);
    }

    @Override
    public void close() {

    }
}
