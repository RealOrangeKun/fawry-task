package org.example.task.interfaces;

import java.util.Date;

// Interface for products with expiration dates
public interface Expirable {
    boolean isExpired();       // Check if product has expired
    void setExpirationDate(Date expirationDate);
    Date getExpirationDate();
}
