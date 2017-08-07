/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

/**
 *
 * @author Aspire
 */
public class Enrutamiento {
    
    String nodo ;
    String socketString;
    int distancia;

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getSocket() {
        return socketString;
    }

    public void setSocket(String socketString) {
        this.socketString = socketString;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
