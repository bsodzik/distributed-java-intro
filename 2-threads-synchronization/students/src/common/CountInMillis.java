/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author s396474
 */
public class CountInMillis {
    
    private long time;
    
    public  CountInMillis(){
        start();
    }
    
    public long getMillis() {
        return System.currentTimeMillis() - time;
    }
    
    public void start(){
        time = System.currentTimeMillis();
    }
    
}
