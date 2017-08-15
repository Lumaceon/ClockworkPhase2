package lumaceon.mods.clockworkphase2.capabilities.mode;

public class ModeHandler implements IModeHandler
{
    protected int mode, numberOfModes;

    public ModeHandler(int defaultMode, int numberOfModes) {
        this.mode = defaultMode;
        this.numberOfModes = numberOfModes;
    }

    @Override
    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public int getMode() {
        return this.mode;
    }

    @Override
    public int nextMode()
    {
        if(mode+1 == numberOfModes)
        {
            mode = 0;
            return mode;
        }
        return ++this.mode;
    }

    @Override
    public int previousMode()
    {
        if(mode == 0)
        {
            mode = numberOfModes - 1;
            return mode;
        }
        return --this.mode;
    }
}
