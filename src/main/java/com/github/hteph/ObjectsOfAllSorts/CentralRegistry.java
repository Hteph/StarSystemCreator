package com.github.hteph.ObjectsOfAllSorts;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**

 */
public class CentralRegistry {


    private static Map<String, StellarObject> archive = new HashMap<>();

    public static boolean putInArchive(StellarObject something){

        if(something.getArchiveID().isEmpty()) return false;

        //TODO more verification and such stuff are needed

        archive.put(something.getArchiveID(),something);
        return true;

    }

    public static StellarObject getFromArchive(String archiveID){

        if(archive.containsKey(archiveID)) return archive.get(archiveID);
        else return new ErrorObject("Key not found");
    }


    public static StellarObject getAndRemoveFromArchive(String archiveID) {

        if(archive.containsKey(archiveID)) return archive.remove(archiveID);
        else return new ErrorObject("Key not found");
    }
}
