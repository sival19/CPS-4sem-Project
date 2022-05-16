package dk.sdu.sem4.Domain;

import com.hazelcast.topic.Message;

public class TopicListener implements com.hazelcast.topic.MessageListener<String> {

    String msg;

    @Override
    public void onMessage(Message<String> message) {


        msg = message.getMessageObject();

        System.out.println("message from publisher: " + message.getMessageObject());
    }

    public String getMsg() {
        return msg;
    }
}
