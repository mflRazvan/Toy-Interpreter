package model.type;

import model.value.*;

public class StringType  implements Type{
    @Override
    public Value getDefaultValue(){
        return new StringValue("");
    }

    @Override
    public boolean equals(Type obj){
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
}
