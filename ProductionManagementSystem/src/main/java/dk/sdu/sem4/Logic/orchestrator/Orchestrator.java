package dk.sdu.sem4.Logic.orchestrator;

import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import dk.sdu.sem4.Logic.Assembly.AssemblySubscriber;

import dk.sdu.sem4.Logic.ISubscriber;

import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;
import org.json.JSONObject;

public class Orchestrator implements IOrchestrator {


    ISubscriber agv;
    ISubscriber wh;
    ISubscriber assembly;

    int battery = 100;
    String agvProgramName;
    int agvState;

    int assemblyState;
    int assemblyProgramName;

    int warehouseState;


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
    public String getAssemblyStatus() {
        return null;
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
    public int getAgvState() {
        if (agv.getMessage() != null) {
            String json = agv.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            agvState = ob.getInt("state");
        }
        return agvState;
    }

    @Override
    public int getWarehouseState() {
        if (wh.getMessage() != null) {
            String json = wh.getMessage();
//          parsing JSON message from Warehouse
            JSONObject ob = new JSONObject(json);
             warehouseState= ob.getInt("State");
        }
        return warehouseState;
    }

    @Override
    public int getAGVstate() {
        return 0;
    }

    @Override
    public int getAGVbattery() {
        if (agv.getMessage() != null) {
            String json = agv.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            battery = ob.getInt("battery");
        }

        return battery;
    }

    @Override
    public int getAssemblyState() {
        if (assembly.getMessage() != null) {
            String json = assembly.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            assemblyState = ob.getInt("State");
        }
        return assemblyState;
    }

    @Override
    public int getAssemblyProgram() {
        if (assembly.getMessage() != null) {
            String json = assembly.getMessage();
//            parsing JSON message from AGV to battery, state, program name variables
            JSONObject ob = new JSONObject(json);
            assemblyProgramName= ob.getInt("CurrentOperation");
        }
        return assemblyProgramName;
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

}
