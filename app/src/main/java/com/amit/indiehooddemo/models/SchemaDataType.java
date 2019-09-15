package com.amit.indiehooddemo.models;

public class SchemaDataType {

    private String type;
    private String num;

    public SchemaDataType(String type, String num) {
        this.type = type;
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isPrimitive(){
        if(type.equalsIgnoreCase("string") ||
                type.equalsIgnoreCase("int") ||
                type.equalsIgnoreCase("float") ||
                type.equalsIgnoreCase("bool"))
            return true;

        return false;
    }

    public boolean isImageType(){
        if(type.equalsIgnoreCase("image_type")){
            return true;
        }

        return false;
    }

    public boolean isAddressType(){
        if(type.equalsIgnoreCase("address_type")){
            return true;
        }

        return false;
    }

    public boolean isArray(){
        if(num.equalsIgnoreCase("1+")){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SchemaDataType{" +
                "type='" + type + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
