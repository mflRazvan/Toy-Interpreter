package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type type){
        this.address = address;
        this.locationType = type;
    }

    public int getAddress(){return address;}

    @Override
    public Type getType() { return new RefType(locationType);}

    @Override
    public boolean equals(Value another){
        if (another instanceof RefValue)
            return address == ((RefValue)another).address && this.getType().equals(another.getType());
        else
            return false;
    }

    @Override
    public String toString(){
        return "Reference(" + address + ")";
    }
}
