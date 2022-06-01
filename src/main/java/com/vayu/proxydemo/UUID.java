package com.vayu.proxydemo;

public class UUID {
    private String uuid;

    public UUID(String uuid) {
        this.uuid = uuid;
    }

    public UUID() {
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "UUID{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
