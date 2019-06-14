package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;

/**
 */
public class ErrorObject extends StellarObject {

    public ErrorObject(String errorMessage) {
        super("Error",errorMessage,null);
    }

    @Override
    public BigDecimal getOrbitalDistance() {
        return null;
    }
}
