package lumaceon.mods.clockworkphase2.capabilities;

public interface IActivatableHandler
{
    public void setActive(); //Sets active to !getActive.
    public void setActive(boolean isActive);
    public boolean getActive();
}
