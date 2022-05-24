package dk.sdu.sem4.Logic.orchestrator;

import dk.sdu.sem4.Logic.AGVsubscriber;
import dk.sdu.sem4.Logic.AssemblySubscriber;

import dk.sdu.sem4.Logic.ISubscriber;

import dk.sdu.sem4.Logic.WarehouseSubscriber;
import org.json.JSONObject;

public class Orchestrator implements IOrchestrator {


    ISubscriber agv;
    ISubscriber wh;
    ISubscriber assembly;

    int battery = 100;

    int agvState;
    int assemblyState;
    int assemblyProgramName;
    int warehouseState;
    String programNameAGV;

    boolean assmblyHasItem = false;
    boolean WHHasItem = false;
    boolean AGVatAssembly = false;
    boolean sendAssembly = false;
    boolean sendWarehouse = true;
    boolean AGVatWH = false;
    boolean WHReady = true;
    boolean AGVHasItem = false;
    boolean itemAssembled = false;
    boolean WHPut = false;
    boolean WHPick = true;
    boolean needCharge = false;
    boolean startSystem = true;

    int whItem = 1;
    int oldWHItem;
    static int state;


    public Orchestrator() {
        wh = new WarehouseSubscriber();
        agv = new AGVsubscriber();
        assembly = new AssemblySubscriber();
    }

    @Override
    public String getAssemblyStatus() {
        return null;
    }


    // start sequence in gui
    @Override
    public void startSequence() throws InterruptedException {
        int oldState = getAgvState();
        state = 0;
        if (oldState != state){
            state = oldState;
        }

        Thread t9 = new Thread(() -> {
        });

        if (startSystem){
            agv.SendMessage("MoveToChargerOperation");
            state = 0;
            startSystem = false;
        }

        if (state == 1 && !WHHasItem && getWarehouseState() == 0 && WHReady && WHPick) {
            wh.SendMessage("PickItemWarehouseOperation," + whItem);
            oldWHItem = whItem;
            whItem += 1;
            WHHasItem = true;
            WHReady = false;
        }


        if (state == 1 && sendWarehouse && !needCharge) {
            agv.SendMessage("MoveToStorageOperation");
            AGVatWH = true;
            sendWarehouse = false;
            state = 0;
        }


        if (state == 1 && WHHasItem && AGVatWH && !needCharge) {

            agv.SendMessage("PickWarehouseOperation");
            WHHasItem = false;
            sendAssembly = true;
            AGVHasItem = true;
            WHPick = false;
            WHPut = true;
            state = 0;
        }

        if (getAssemblyState() == 0 && state == 1 && sendAssembly && !needCharge) {
            agv.SendMessage("MoveToAssemblyOperation");
            AGVatAssembly = true;
            sendAssembly = false;
            AGVatWH = false;
            state = 0;
        }


        if (state == 1 && AGVatAssembly && AGVHasItem && !AGVatWH && !needCharge) {
            agv.SendMessage("PutAssemblyOperation");
            assmblyHasItem = true;
            AGVHasItem = false;
            state = 0;
        }


        if (assmblyHasItem && !itemAssembled && getAssemblyState() == 0 && state == 1) {
            assembly.SendMessage("1234");
            itemAssembled = true;
            state = 0;
        }


        if (assmblyHasItem && itemAssembled && getAssemblyState() == 0 && state == 1 && !AGVatWH && !needCharge) {
            agv.SendMessage("PickAssemblyOperation");
            assmblyHasItem = false;
            itemAssembled = false;
            AGVHasItem = true;
            sendWarehouse = true;
            state = 0;
        }

        if (state == 1 && sendWarehouse && AGVHasItem && !needCharge){
            agv.SendMessage("MoveToStorageOperation");
            sendWarehouse = false;
            AGVatWH = true;
            AGVatAssembly = false;
            state = 0;
        }

        if (state == 1 && AGVHasItem && !WHHasItem && WHPut && AGVatWH && !needCharge){
            agv.SendMessage("PutWarehouseOperation");
            AGVHasItem = false;
            WHHasItem = true;
        }

        if (state == 1 && getWarehouseState() == 0 && WHPut && AGVatWH && !WHReady){
            wh.SendMessage("InsertItemWarehouseOperation," + oldWHItem + ",This is item" + oldWHItem);
            WHPut = false;
            WHPick = true;
            WHReady = true;
            WHHasItem = false;
        }

        if (getAGVbattery() < 10 && state == 1){
            needCharge = true;
            agv.SendMessage("MoveToChargerOperation");
            state = 0;
        }

        if (getAGVbattery() > 10 && state == 1){
            needCharge = false;
            state = 0;
        }

        Thread.sleep(100);


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
            warehouseState = ob.getInt("State");
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
            assemblyProgramName = ob.getInt("CurrentOperation");
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
