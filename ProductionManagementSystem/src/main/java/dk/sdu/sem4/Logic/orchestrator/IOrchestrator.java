package dk.sdu.sem4.Logic.orchestrator;

public interface IOrchestrator {

    String getWarehuseState();

    String getAssemblyStatus();
    int getAgvState();
    int getWarehouseState();
//    int getAGVstate();
    int getAGVbattery();
    int getAssemblyState();
    int getAssemblyProgram();
    void startProductionSequence();
    void stopProductionSequence();
    void abortProductionSequence();
    void pauseProductionSequence();
    String getAGVProgram();
    void startSequence();


}
