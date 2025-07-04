package org.example.task.interfaces;

import java.util.Date;

public interface Expirable {
    boolean isExpired();       
    void setExpirationDate(Date expirationDate);
    Date getExpirationDate();
}
