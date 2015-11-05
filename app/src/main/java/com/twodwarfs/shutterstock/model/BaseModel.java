package com.twodwarfs.shutterstock.model;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 *
 * Base abstract model that others inherit.
 * Generally I am making this one implement Parcelable so that other subclasses
 * are forced to implement it, but for the purpose of the task that'd be an overkill.
 */
public abstract class BaseModel {

    /** returns the type of the model, used in the Adapter later **/
    public abstract int getType();
}
