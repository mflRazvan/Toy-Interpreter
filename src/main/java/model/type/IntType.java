package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {

    @Override
    public Value getDefaultValue(){
        return new IntValue(0);
    }

    @Override
    public boolean equals(Type obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
