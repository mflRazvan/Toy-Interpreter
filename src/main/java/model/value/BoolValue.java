package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v){
        val=v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "Value is " + val;
    }

    @Override
    public boolean equals(Value obj) {
        return obj instanceof BoolValue;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }
}