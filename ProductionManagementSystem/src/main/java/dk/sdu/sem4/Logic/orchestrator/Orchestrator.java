package dk.sdu.sem4.Logic.orchestrator;

import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import dk.sdu.sem4.Logic.ISubscriber;
import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;
import org.json.JSONObject;

public class Orchestrator implements IOrchestrator {



    ISubscriber agv;
    ISubscriber wh;

    int battery = 100;
    String programName;
    int state;

    public Orchestrator() {
        wh = new WarehouseSubscriber();
        agv = new AGVsubscriber();
    }


    @Override
    public String getWarehuseState() {
        return null;
    }

    @Override
    public String getAssemblyStatus() {
        return null;
    }

    private void sequenceInitializer(){


    }

    // start sequence in gui
    @Override
    public void startSequence(){
        sequenceInitializer();
        //
        //
        //
    }

    @Override
    public int getAGVstate() {
        if (agv.getMessage() != null) {
            String json = agv.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            state = ob.getInt("state");

        }
        return state;
    }

    @Override
    public int getAGVbattery() {
        if (agv.getMessage() != null) {
            String json = agv.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            battery = ob.getInt("battery");

//            System.out.println(json);
        }

        return battery;
    }

    @Override
    public void startProductionSequence() {

    }

    @Override
    public void stopProductionSequence() {

    }

    @Override
    public void abortProductionSequence() {

    }

    @Override
    public void pauseProductionSequence() {

    }

    @Override
    public String getAGVProgram() {
        if (agv.getMessage() != null) {
            String json = agv.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            programName = ob.getString("program name");
        }
        return programName;
    }
}
