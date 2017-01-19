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
public class BattleHandler {

    public BattleHandler() {
    }

    public static String startingBattle(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) {
        ws.BattleHandlerBeanService service = new ws.BattleHandlerBeanService();
        ws.BattleHandlerBean port = service.getBattleHandlerBeanPort();
        return port.startingBattle(arg0, arg1, arg2);
    }
    
    
}
