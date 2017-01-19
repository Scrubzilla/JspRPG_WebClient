/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

/**
 *
 * @author Nicklas
 */
public class ZoneManager {
    String[] zoneContainer = {"Noob Valley", "Sunset Beach", "Forest of Glory"};
    
    public ZoneManager() {
    }
    
    public int nameToId(String name){
        for(int i = 0; i < zoneContainer.length; i++){
            if(name.equals(zoneContainer[i])){
                return i+1;
            }
        }
        
        return -1;
    }
    
    public String idToName(int id){
        int returnId = id-1;
        return zoneContainer[returnId];
    }
    
    public String getAllZones(){
        String zones = "";
        
        for(int i = 0; i < zoneContainer.length; i++){
            if(i == 0){
                zones = zoneContainer[i];
            }else{
                zones = zones + "\n" + zoneContainer[i];
            }
        }
        
        return zones;
    }
    
}
