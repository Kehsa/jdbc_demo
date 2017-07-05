package com.kehsa.phone;

import java.util.EventListener;

/**
 * Change event listener interface.
 * create on 03.10.15
 * @author kehsa
 */
public interface StateChangeListener extends EventListener {
    /** Signal emit then any field of object is modified. */
    void stateChanged();
}
