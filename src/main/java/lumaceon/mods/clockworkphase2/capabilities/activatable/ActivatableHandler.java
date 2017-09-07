package lumaceon.mods.clockworkphase2.capabilities.activatable;

import javax.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable final Object obj)
    {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        final IActivatableHandler that = (IActivatableHandler) obj;

        return active == that.getActive();
    }
}
