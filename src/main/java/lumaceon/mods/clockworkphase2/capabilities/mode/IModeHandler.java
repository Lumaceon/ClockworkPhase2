package lumaceon.mods.clockworkphase2.capabilities.mode;

public interface IModeHandler
{
    public void setMode(int mode);
    public int getMode();

    public int nextMode();
    public int previousMode();
}
