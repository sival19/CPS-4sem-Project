package dk.sdu.sem4.Logic;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseSubscriberTest {
    private static long testStartTime;
    private static long finishTestTime;
    private final ISubscriber wh = new WarehouseSubscriber();

    @Test
    void sendMessage() {
        // Setup
        testStartTime = System.currentTimeMillis();
        // Pre Asserts
        int expResult = 1;
        // Tests
        // send a task to AGV
        wh.SendMessage("PickItemWarehouseOperation,1");
        // wait for response from AGV
        int result;
        while(true){
            String message = wh.getMessage();
            System.out.println(message);
            if(message!=null){
                JSONObject ob = new JSONObject(message);
                // the result for sending a task is a change of state to executing
                result = ob.getInt("State");
                finishTestTime = System.currentTimeMillis();
                break;
            }
        }
        // confirming that warehouse state changes to executing after receiving a task
        assertEquals(expResult, result);
    }

    @Test
    void getMessage() {
        // Pre Asserts
        // we expect that the message will be received in under 5 seconds
        boolean expRes = true;
        // Tests
        long timePassed = finishTestTime - testStartTime;
        // we expect the time passed to be less than 5 seconds
        boolean res =(timePassed<5000);
        // confirming that the time for getting an AGV message is under 5 seconds
        assertEquals(expRes, res);
        System.out.println("Time used to get a response to task from Warehouse "+ timePassed/1000+" sec");
    }
}