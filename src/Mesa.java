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
	
	public void run() {
		System.out.println("Iniciando o jantar");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
			PrintStream pstream = new PrintStream(csocket.getOutputStream());
			boolean keepgoing = true;
			while(keepgoing){
				String line = in.readLine();
				if(line.split(" ").length == 2){
					String comando = line.split(" ")[0];
					String recurso = line.split(" ")[1];
					try {
						if(comando.equals("release") && recurso.equals("1")){
							mutexRecurso1.release();
							pstream.println("true");
						} else if(comando.equals("release") && recurso.equals("2")){
							mutexRecurso2.release();
							pstream.println("true");
						} else if(comando.equals("release") && recurso.equals("3")){
							mutexRecurso3.release();
							pstream.println("true");
						} else if(comando.equals("release") && recurso.equals("4")){
							mutexRecurso4.release();
							pstream.println("true");
						} else if(comando.equals("release") && recurso.equals("5")){
							mutexRecurso5.release();
							pstream.println("true");	
						} else if(comando.equals("acquire") && recurso.equals("1")){
							boolean result = mutexRecurso1.tryAcquire(1, 1, TimeUnit.SECONDS);
							pstream.println(" " + result);
						} else if(comando.equals("acquire") && recurso.equals("2")){		
							boolean result = mutexRecurso2.tryAcquire(1, 1, TimeUnit.SECONDS);
							pstream.println(" " + result);
						} else if(comando.equals("acquire") && recurso.equals("3")){		
							boolean result = mutexRecurso3.tryAcquire(1, 1, TimeUnit.SECONDS);
							pstream.println(" " + result);	
						} else if(comando.equals("acquire") && recurso.equals("4")){		
							boolean result = mutexRecurso4.tryAcquire(1, 1, TimeUnit.SECONDS);
							pstream.println(" " + result);
						} else if(comando.equals("acquire") && recurso.equals("5")){		
							boolean result = mutexRecurso5.tryAcquire(1, 1, TimeUnit.SECONDS);
							pstream.println(" " + result);
						} else if (comando.equals("finish") && recurso.equals("0")){
							keepgoing = false;
							pstream.println("true");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
