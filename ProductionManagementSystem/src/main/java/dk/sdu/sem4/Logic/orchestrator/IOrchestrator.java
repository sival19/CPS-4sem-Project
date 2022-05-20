package dk.sdu.sem4.Logic.orchestrator;

public interface IOrchestrator {

    String getWarehuseState();
    int getAssemblyState();
    int getAGVstate();
    int getAGVbattery();
    void startProductionSequence();
    void stopProductionSequence();
    void abortProductionSequence();
    void pauseProductionSequence();
    String getAGVProgram();
    void startSequence();
    int getAssemblyProgram();


}
