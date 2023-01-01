package me.seungpang.redis;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements MessageListener {

    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void enterChatRoom(String chatRoomName) {
        container.addMessageListener(this, new ChannelTopic(chatRoomName));
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("q")) {
                System.out.println("Quit");
                break;
            }
            redisTemplate.convertAndSend(chatRoomName, line);
        }

        container.removeMessageListener(this);
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        System.out.println("Message " + message.toString());
    }
}
