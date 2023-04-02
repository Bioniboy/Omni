package com.bion.omni.omnimod.util;

public class PowerConfig {
    private final String id;
    private Integer value;
    public PowerConfig(String id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    public String getId() {
        return id;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
