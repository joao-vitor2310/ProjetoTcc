/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.jbdc;
import javax.swing.JOptionPane;

/**
 *
 * @author joaov
 */ 
public class TesteConexao {
    
    public static void main(String[] args) {
        
        try {
            new ConnectionFactory().getConnection();
            JOptionPane.showMessageDialog(null,"Conectado com successo");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Houve um erro" + erro);
        }
    }
    
}
