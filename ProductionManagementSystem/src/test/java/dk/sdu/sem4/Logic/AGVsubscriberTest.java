package dk.sdu.sem4.Logic;

import dk.sdu.sem4.Domain.TopicHandler;
import dk.sdu.sem4.Logic.AGVsubscriber;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AGVsubscriberTest {

    private TopicHandler topicSubscriber;
    private String message;

    public AGVsubscriberTest()
    {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("AGVSubTopic");
    }


    @Test
    public void AGV_to_Warehouse()
    {
        // Idle  : Verify
        // Sender AGV til WH --> Executing  : Verify
        // Når til WH --> Idle  : assertEquals

        System.out.println("Testing: AGV going to WH");

        AGVsubscriber agv = Mockito.mock(AGVsubscriber.class);
        Mockito.verify();
    }
}





