import com.hazelcast.topic.Message;

public class TopicListener implements com.hazelcast.topic.MessageListener<String> {
    @Override
    public void onMessage(Message<String> message) {
        System.out.println("message from publisher: " + message.getMessageObject());
    }
}
