package Logic.Systems.Environment;

import Logic.Systems.Environment.WallSystem.WallSystem;

public class EnvironmentSystem {
    private WallSystem wallSystem;

    public EnvironmentSystem(){
        this.wallSystem = new WallSystem();
    }

    public WallSystem getWallSystem(){
        return wallSystem;
    }

    public void setWallSystem(WallSystem wallSystem) {
        this.wallSystem = wallSystem;
    }
}
