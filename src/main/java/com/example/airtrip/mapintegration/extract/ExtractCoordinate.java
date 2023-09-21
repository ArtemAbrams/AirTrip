package com.example.airtrip.mapintegration.extract;

import com.example.airtrip.mapintegration.Geometry;
import com.example.airtrip.mapintegration.Location;
import com.example.airtrip.mapintegration.ResponseFromMapServer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExtractCoordinate {
    public Location getLocation(ResponseFromMapServer responseFromMapServer){
        if(responseFromMapServer != null && responseFromMapServer.getStatus().equals("OK")){
            for (var result : responseFromMapServer.getResults()){
                if(result.getGeometry()!=null){
                   return result.getGeometry().getLocation();
                }
            }
        }
        return null;
    }
}
