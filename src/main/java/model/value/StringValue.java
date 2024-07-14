package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value{
    String val;

    public StringValue(String v){
        val=v;
    }

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Value obj) {
        return obj instanceof StringValue;
    }

    @Override
    public String toString() {
        return "Value = " + val;
    }
}
