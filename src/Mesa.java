import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Mesa implements Runnable {
	private static Semaphore mutexRecurso1 = new Semaphore(1);
	private static Semaphore mutexRecurso2 = new Semaphore(1);
	private static Semaphore mutexRecurso3 = new Semaphore(1);
	private static Semaphore mutexRecurso4 = new Semaphore(1);
	private static Semaphore mutexRecurso5 = new Semaphore(1);
	private static String garfo1;
	private static String garfo2;
	private static String garfo3;
	private static String garfo4;
	private static String garfo5;
	
	private Socket csocket;
	
	public static void main(String[] args){
		System.out.println("Ariando as panelas, abrindo as portas do restaurante! (Iniciando despachante) \n");
		ServerSocket ssock;
		try {
			ssock = new ServerSocket(15695);
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
				//System.out.println(line);
				if(line == null){System.out.println("Deu ruim!");}
				else if(line.split(" ").length == 3){
					String comando = line.split(" ")[0];
					String recurso = line.split(" ")[1];
					String filosofo = line.split(" ")[2];
					try {
						if(comando.equals("release") && recurso.equals("1")){
							mutexRecurso1.release();
							garfo1 = "";
							//System.out.println("Release");
							pstream.println("true release 1 " + filosofo);
						} else if(comando.equals("release") && recurso.equals("2")){
							mutexRecurso2.release();
							garfo2 = "";
							//System.out.println("Release");
							pstream.println("true release 2 " + filosofo);
						} else if(comando.equals("release") && recurso.equals("3")){
							mutexRecurso3.release();
							garfo3 = "";
							System.out.println("Release");
							pstream.println("true release 3 " + filosofo);
						} else if(comando.equals("release") && recurso.equals("4")){
							mutexRecurso4.release();
							garfo4 = "";
							//System.out.println("Release");
							pstream.println("true release 4 " + filosofo);
						} else if(comando.equals("release") && recurso.equals("5")){
							mutexRecurso5.release();
							garfo5 = "";
							//System.out.println("Release");
							pstream.println("true release 5 " + filosofo);	
						} else if(comando.equals("acquire") && recurso.equals("1")){
							boolean result = mutexRecurso1.tryAcquire(1, 1, TimeUnit.SECONDS);
							if(result == true){
								garfo1 = filosofo;
								//System.out.println("Garfo 1 = " + garfo1);
							}
							pstream.println("" + result +" 1 Ac " + filosofo);
						} else if(comando.equals("acquire") && recurso.equals("2")){		
							boolean result = mutexRecurso2.tryAcquire(1, 1, TimeUnit.SECONDS);
							if(result == true){
								garfo2 = filosofo;
								//System.out.println("Garfo 2 = " + garfo2);
							}
							pstream.println("" + result +" 2 Ac " + filosofo);
						} else if(comando.equals("acquire") && recurso.equals("3")){		
							boolean result = mutexRecurso3.tryAcquire(1, 1, TimeUnit.SECONDS);
							if(result == true){
								garfo3 = filosofo;
								//System.out.println("Garfo 3 = " + garfo3);
							}
							pstream.println("" + result +" 3 Ac " + filosofo);	
						} else if(comando.equals("acquire") && recurso.equals("4")){		
							boolean result = mutexRecurso4.tryAcquire(1, 1, TimeUnit.SECONDS);
							if(result == true){
								garfo4 = filosofo;
								//System.out.println("Garfo 4 = " + garfo4);
							}
							pstream.println("" + result +" 4 Ac " + filosofo);
						} else if(comando.equals("acquire") && recurso.equals("5")){		
							boolean result = mutexRecurso5.tryAcquire(1, 1, TimeUnit.SECONDS);
							if(result == true){
								garfo5 = filosofo;
								//System.out.println("Garfo 5 = " + garfo5);
							}
							pstream.println("" + result +" 5 Ac " + filosofo);
						} else if (comando.equals("finish") && recurso.equals("0")){
							keepgoing = true;
							pstream.println("true fim" + filosofo);

						}
						System.out.println("Garfos: " + garfo1 + garfo2 + garfo3 + garfo4 + garfo5);
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
