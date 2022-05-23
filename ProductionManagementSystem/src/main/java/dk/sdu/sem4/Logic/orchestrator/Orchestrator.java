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
//    int programNameAssembly;
//    int state;
//    int stateAssembly;

    boolean assmblyHasItem = false;
    boolean WHHasItem = false;
    boolean AGVatAssembly = false;
    boolean sendAssembly = false;
    boolean sendWarehouse = true;
    boolean AGVatWH = false;
    boolean WHReady = true;
    boolean AGVHasItem = false;
    boolean itemAssembled = false;

    int AGVstate;
    int AGVchangedState = 0;

    int whItem = 1;
    int oldWHItem;


    public Orchestrator() {
        wh = new WarehouseSubscriber();
        agv = new AGVsubscriber();
        assembly = new AssemblySubscriber();
    }

    @Override
    public String getAssemblyStatus() {
        return null;
    }


    private void sequenceInitializer(){

    }

    // start sequence in gui
    @Override
    public void startSequence() throws InterruptedException {
//        sequenceInitializer();
//        System.out.println(getAGVstate());
//        System.out.println(getAssemblyState());
        //
        //
        //
        if (!WHHasItem && getWarehouseState() == 0 && WHReady){
            wh.SendMessage("PickItemWarehouseOperation," + whItem);
            oldWHItem = whItem;
            whItem += 1;
            WHHasItem = true;
            WHReady = false;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (getAgvState() == 0 && sendWarehouse){
            agv.SendMessage("MoveToStorageOperation");
            AGVatWH = true;
            sendWarehouse = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (getAgvState() == 0 && WHHasItem && AGVatWH && stateChangeAGV()){
            agv.SendMessage("PickWarehouseOperation");
            WHHasItem = false;
            sendAssembly = true;
            AGVHasItem = true;
            Thread.sleep(1000);
        }

        if (getAssemblyState() == 0 && getAgvState() == 1 && sendAssembly){
            agv.SendMessage("MoveToAssemblyOperation");
            AGVatAssembly = true;
            sendAssembly = false;
            AGVatWH = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (getAgvState()==1 && AGVatAssembly && AGVHasItem){
            agv.SendMessage("PutAssemblyOperation");
            assmblyHasItem = true;
            AGVHasItem = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (assmblyHasItem && !itemAssembled && getAssemblyState() == 0){
            assembly.SendMessage("1234");
            itemAssembled = true;

            Thread.sleep(1000);
        }

        if (assmblyHasItem && itemAssembled && getAssemblyState() == 0 && getAgvState() == 1){
            agv.SendMessage("PickAssemblyOperation");
            assmblyHasItem = false;
            itemAssembled = false;
            AGVHasItem = true;

            Thread.sleep(1000);
        }

//        if (getAssemblyProgram() == 1234 && getAssemblyState() == 0){
//            assembly.SendMessage("1111");
//        }

//        agv.SendMessage("PutAssemblyOperation");
//        assembly.SendMessage("2222");
    }

    public boolean stateChangeAGV(){
        boolean flag = false;
        if (AGVstate != AGVchangedState){
            AGVchangedState = AGVstate;
            flag = true;
        }
        return flag;
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

//    @Override
//    public int getAGVstate() {
//        return 0;
//    }

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
