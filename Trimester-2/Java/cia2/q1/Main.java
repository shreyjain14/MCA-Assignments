class Main {
    public static void main(String args[]) {

        Tractor tractor = new Tractor("cool tractor", "tractor 2.0", "petrol");
        tractor.setToolName("tractor 3.0");
        System.out.println(tractor.getToolName());
        tractor.manageCrop("Wheat");
        tractor.performMaintenance();
        
    }
}

abstract class Tool {
    String toolName;
    
    protected String setToolName(String toolName) {
        this.toolName = toolName;
        return toolName;
    }
    public String getToolName() {
        return toolName;
    }
}

interface Maintenance {
    void performMaintenance();
}

interface CropManagement {
    void manageCrop(String cropType);
}

class Machine extends Tool implements Maintenance {
    String machineType;

    Machine(String toolName, String machineType) {
        this.machineType = machineType;
        super.toolName = toolName;
    }

    @Override
    public void performMaintenance() {
        System.out.println("Maintenance is being performed!");
    }
}

class Tractor extends Machine implements CropManagement {
    String fuelType;

    Tractor(String toolName, String machineType, String fuelType) {
        super(toolName, machineType);
        this.fuelType = fuelType;
    }

    @Override
    public void manageCrop(String cropType) {
        System.out.println(cropType + " are being maintened using Tractor!");
    }
}