package dk.sdu.sem4.Logic;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssemblySubscriberTest {
    private static long testStartTime;
    private static long finishTestTime;
    private final ISubscriber assembly = new AssemblySubscriber();

    @Test
    void sendMessage() {
        // Setup
        testStartTime = System.currentTimeMillis();
        // Pre Asserts
        String expResult = "1234";
        // Tests
        // send a task to AGV
        assembly.SendMessage("1234");
        // wait for response from AGV
        String result;
        while(true){
            String message = assembly.getMessage();
            if(message!=null){
                JSONObject ob = new JSONObject(message);
                result = String.valueOf(ob.getInt("CurrentOperation"));
                finishTestTime = System.currentTimeMillis();
                break;
            }
        }
        // confirming that the message received from Assembly is the same as the task sent
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
        System.out.println("Time used to get a response to task from AGV "+ timePassed/1000+" sec");
    }

}