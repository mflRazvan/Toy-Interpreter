package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value{
    int val;
    public IntValue(int v){
        val=v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "Value = " + val;
    }

    @Override
    public boolean equals(Value obj) {
        return obj instanceof IntValue;
    }

    @Override
    public Type getType() {
        return new IntType();
    }
}