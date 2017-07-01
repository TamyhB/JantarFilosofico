import java.util.concurrent.Semaphore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Mesa implements Runnable {
	private static Semaphore mutexRecurso1 = new Semaphore(1);
	private static Semaphore mutexRecurso2 = new Semaphore(1);
	private static Semaphore mutexRecurso3 = new Semaphore(1);
	private static Semaphore mutexRecurso4 = new Semaphore(1);
	private static Semaphore mutexRecurso5 = new Semaphore(1);
	private static int nFilosofo = 0;
	private Socket csocket;
	
	public static void main(String[] args){
		System.out.println("Ariando as panelas, abrindo as portas do restaurante! (Iniciando despachante) \n");
		ServerSocket ssock;
		try {
			ssock = new ServerSocket(15699);
			System.out.println("Bem vindos ao nosso restaurante, esperamos que tenham uma ótima noite e desfrutem de nossa ótima refeição! ");
		    System.out.println("Estamos ouvindo a sua solicitação de pedido :) \n");
			while (true) {
		    	Socket sock = ssock.accept();
		        System.out.println("Pedido anotado! (conectado)");
		        nFilosofo++;
		        Thread t = new Thread(new Mesa(sock));
		        t.start();   
		        System.out.println("Sente-se por favor. Iniciaremos o atendimento imediatamente!");
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
	
	Mesa(Socket csocket) {
	      this.csocket = csocket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
