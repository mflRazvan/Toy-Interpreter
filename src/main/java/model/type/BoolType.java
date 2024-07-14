package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type {

    @Override
    public Value getDefaultValue(){
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Type obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
