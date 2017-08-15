package lumaceon.mods.clockworkphase2.capabilities.activatable;

public class ActivatableHandler implements IActivatableHandler
{
    boolean active = false;

    @Override
    public void setActive() {
        active = !active;
    }

    @Override
    public void setActive(boolean isActive) {
        active = isActive;
    }

    @Override
    public boolean getActive() {
        return active;
    }
}
