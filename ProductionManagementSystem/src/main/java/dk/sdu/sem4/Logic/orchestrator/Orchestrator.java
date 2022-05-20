package dk.sdu.sem4.Logic.orchestrator;

import dk.sdu.sem4.Logic.AGV.AGVsubscriber;

import dk.sdu.sem4.Logic.AGV.IAGVsubscriber;
import dk.sdu.sem4.Logic.Assembly.AssemblySubscriber;
import dk.sdu.sem4.Logic.Assembly.IAssemblySubscriber;
import dk.sdu.sem4.Logic.WH.IWHsubscriber;

import dk.sdu.sem4.Logic.ISubscriber;

import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;
import org.json.JSONObject;

public class Orchestrator implements IOrchestrator {




    IAGVsubscriber agv;
    IWHsubscriber wh;
    IAssemblySubscriber assembly;

    ISubscriber agv;
    ISubscriber wh;


    int battery = 100;
    String programNameAGV;
    int programNameAssembly;
    int state;
    int stateAssembly;
    boolean hasItem = false;
    String AGVLastOperation;
    boolean put = false;
    boolean sendAssembly = true;

    public Orchestrator() {
        wh = new WarehouseSubscriber();
        agv = new AGVsubscriber();
        assembly = new AssemblySubscriber();
    }


    @Override
    public String getWarehuseState() {
        return null;
    }

    @Override
    public int getAssemblyState() {
        if (assembly.getMessage() != null){
            String json = assembly.getMessage();
            JSONObject object = new JSONObject(json);
            stateAssembly = object.getInt("State");
        }
        return stateAssembly;
    }

    private void sequenceInitializer(){


    }

    // start sequence in gui
    @Override
    public void startSequence(){
//        sequenceInitializer();
        //
        //
        //
        if (hasItem && getAssemblyState() == 0){
            assembly.SendMessage("2222");
        }

        if (getAGVstate()==1 && put){
            agv.SendMessage("PutAssemblyOperation");
            hasItem = true;
            put = false;
            AGVLastOperation = "PutAssemblyOperation";
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (getAssemblyState() == 0 && getAGVstate() == 1 && sendAssembly){
            agv.SendMessage("MoveToAssemblyOperation");
            put = true;
            sendAssembly = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//        agv.SendMessage("PutAssemblyOperation");
//        assembly.SendMessage("2222");
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
            programNameAGV = ob.getString("program name");
        }
        return programNameAGV;
    }

    @Override
    public int getAssemblyProgram() {
        if (assembly.getMessage() != null) {
            String json = assembly.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            programNameAssembly = ob.getInt("CurrentOperation");
        }
        return programNameAssembly;
    }
}
