package com.hrs.trackingtool.modelvalue;


// Some custom messages
public enum CustomHttpStatus {
    //Guest responses
    GUEST_CHECKEDOUT("200.101", "Guest has already checked out."),
    GUEST_CHECKED("200.102", "Guest has not checked in yet."),
    GUEST_CHECKEDIN("200.103", "Guest has already checked in."),
    GUEST_UNKNOWN("200.104", "Guest status is unknown."),
    //Parcel responses
    PARCEL_DELETED("200.105", "Parcel was deleted successfully."),
    PARCEL_PICKEDUP("200.106", "Parcel was picked up by guest."),
    PARCEL_RETURNED("200.107", "Parcel was returned by guest.");

    private final String value;
    private final String reasonPhrase;

    private CustomHttpStatus(String value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public String getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
