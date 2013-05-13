/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Server;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import msn.MensagemClass;

/**
 *
 * @author Sistemas
 */
public class ServerApp {

    public static void main(String[] args) {
        System.out.println("Iniciando Servidor");
        try {
            ServerSocket servidor = new ServerSocket(1234);
            InputStream entrada;
            BufferedReader read;
            OutputStream saida;
            PrintStream ps;
            while (true) {
                System.out.println("Aguardando conexão....");
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado..." + socket.getRemoteSocketAddress());
                
                entrada = socket.getInputStream();
                read = new BufferedReader(new InputStreamReader(entrada));                
                
                String msgDoCliente = read.readLine();                
                System.out.println("MSG:   " + msgDoCliente);
                
                
                saida = socket.getOutputStream();
                ps = new PrintStream(saida);
                if("Data_Hora".equals(msgDoCliente)){
                    ps.println(new Date().toString());
                                    }
                msgDoCliente = read.readLine();                
                System.out.println("MSG:   " + msgDoCliente);
                if("MSG".equals(msgDoCliente)){
                    String json = read.readLine();
                    System.out.println("Json: "+ json);
                    
                    Gson gson = new Gson();
                    MensagemClass ms = gson.fromJson(json,MensagemClass.class);
                    System.out.println("Destinatario: " + ms.getDestino());
                    ps.println("OK");
                    
                }
                
                System.out.println("Servidor finalizado...");
                read.close();
                entrada.close();
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
