package com.cput.chauma;

import java.io.Serializable;

public class PeerEducatorAdd implements Serializable {
    /**
     * Peer Counselor is the property that should be used when reading and writing to database
     */
    PeerEducatorAdd(){}
    public String EmailAddress;
    public String Name;
    public String Surname;
    public String Password;
    public Boolean IsAuthorised;
}