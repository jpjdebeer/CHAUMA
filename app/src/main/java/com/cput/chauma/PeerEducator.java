package com.cput.chauma;

import java.io.Serializable;

public class PeerEducator implements Serializable {
    /**
     * Peer Counselor is the property that should be used when reading and writing to database
     */
    PeerEducator(){}
    public String ContactNumber;
    public String Course;
    public String EmailAddress;
    public String Gender;
    public String IdNumber;
    public String Name;
    public String Surname;
    public String YearOfStudy;
    public String StudentNumber;
    public String Password;
    public Boolean IsAuthorised;
}


