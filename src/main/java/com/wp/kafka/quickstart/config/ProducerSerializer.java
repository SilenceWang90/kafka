package com.wp.kafka.quickstart.config;

import com.wp.kafka.quickstart.User;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @Description 序列化：泛型为消息的类类型，将泛型类型转为byte[]
 * @Author admin
 * @Date 2023/7/31 13:17
 */
public class ProducerSerializer implements Serializer<User> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, User data) {
        if (data == null) {
            return null;
        }
        byte[] idBytes, nameBytes;
        try {
            String id = data.getId();
            String name = data.getName();
            if (id != null) {
                idBytes = id.getBytes("UTF-8");
            } else {
                idBytes = new byte[0];
            }
            if (name != null) {
                nameBytes = name.getBytes("UTF-8");
            } else {
                nameBytes = new byte[0];
            }
            // 预留int类型所占字节数，idByte和nameByte数组的长度都是int类型，所以是4个byte的长度
            ByteBuffer byteBuffer = ByteBuffer.allocate(4 + 4 + idBytes.length + nameBytes.length);
            // 放入idBytes的实际长度
            byteBuffer.putInt(idBytes.length);
            // 放入idBytes
            byteBuffer.put(idBytes);
            // 放入nameBytes的实际长度
            byteBuffer.putInt(nameBytes.length);
            // 放入nameBytes
            byteBuffer.put(nameBytes);
            return byteBuffer.array();
        } catch (Exception e) {
            // todo：handle Exception
        }
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
