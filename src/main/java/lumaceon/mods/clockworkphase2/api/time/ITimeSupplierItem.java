package lumaceon.mods.clockworkphase2.api.time;

/**
 * Implement this on items which hold time with the purpose of having that time used by other items. Items which
 * need to hold time for themselves and not have it automatically removed should implement ITimeContainerItem instead.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeSupplierItem extends ITimeContainerItem {}