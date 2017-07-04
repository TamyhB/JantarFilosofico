import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Filosofo implements Runnable {
    private boolean gotGarfo1;
    private boolean gotGarfo2;
    private boolean gotGarfo3;
    private boolean gotGarfo4;
    private boolean gotGarfo5;
    private String msgs[];
    private String msgs2[];
    private int maoEsq = 0;
    private int maoDir = 0;
    private String estado = "Estou pensando...";
    int platao = 0;
    int socrates = 0;
    int descartes = 0;
    int aristoteles = 0;
    int tales = 0;

    public int getMaoEsq() {
        return maoEsq;
    }

    public void setMaoEsq(int maoEsq) {
        this.maoEsq = maoEsq;
    }

    public int getMaoDir() {
        return maoDir;
    }

    public void setMaoDir(int maoDir) {
        this.maoDir = maoDir;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void comer(int fil) throws InterruptedException {
		/*
		 * if(this.getMaoEsq() != 0 && this.getMaoDir() == 0){
		 * System.out.println(
		 * "Preciso do garfo da mão direita para começar a comer :(");
		 * this.setEstado("Estou esperando o garfo"); } if(this.getMaoDir() != 0
		 * && this.getMaoEsq() == 0){ System.out.println(
		 * "Preciso do garfo da mão esquerda para começar a comer :(");
		 * this.setEstado("Estou esperando o garfo"); } if(this.getMaoEsq() != 0
		 * && this.getMaoDir() != 0){ setEstado("Estou comendo..."); }
		 */
        Random rand = new Random();

        if (fil == 1) {
            System.out.println("Platão esta comendo agora!");
            Thread.sleep(rand.nextInt(1000));
            msgs2 = new String[] { "release 1", "release 2", "finish 0" };

        } else if (fil == 2) {
            System.out.println("Socrates esta comendo agora!");
            Thread.sleep(rand.nextInt(1000));
            msgs2 = new String[] { "release 1", "release 2", "finish 0" };
        } else if (fil == 3) {
            System.out.println("Descartes esta comendo agora!");
            Thread.sleep(rand.nextInt(1000));
            msgs2 = new String[] { "release 1", "release 2", "finish 0" };
        } else if (fil == 4) {
            System.out.println("Aristoteles esta comendo agora!");
            Thread.sleep(rand.nextInt(1000));
            msgs2 = new String[] { "release 1", "release 2", "finish 0" };
        } else if (fil == 5) {
            System.out.println("Tales esta comendo agora!");
            Thread.sleep(rand.nextInt(1000));
            msgs2 = new String[] { "release 1", "release 2", "finish 0" };
        }
    }

    public void pensar() throws InterruptedException {
        this.setEstado("Estou pensando...");
        System.out.println(this.getEstado());
        Random rand = new Random();
        System.out.println(rand.nextInt(20) * 1000);
        Thread.sleep(rand.nextInt(9) * 1000);
    }

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        Thread t1 = new Thread(
                new Filosofo(new String[] { "acquire 1 Platão", "acquire 2 Platão", "finish 0 Platão" }));
        t1.start();
        Thread t2 = new Thread(
                new Filosofo(new String[] { "acquire 2 Socrates", "acquire 3 Socrates", "finish 0 Socrates" }));
        t2.start();
        Thread t3 = new Thread(
                new Filosofo(new String[] { "acquire 3 Descartes", "acquire 4 Descartes", "finish 0 Descartes" }));
        t3.start();
        Thread t4 = new Thread(new Filosofo(
                new String[] { "acquire 4 Aristoteles", "acquire 5 Aristoteles", "finish 0 Aristoteles" }));
        t4.start();
        Thread t5 = new Thread(new Filosofo(new String[] { "acquire 5 Tales", "acquire 1 Tales", "finish 0 Tales" }));
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }

    public Filosofo(String msgs[]) {
        this.msgs = msgs;
        gotGarfo1 = false;
        gotGarfo2 = false;
        gotGarfo3 = false;
        gotGarfo4 = false;
        gotGarfo5 = false;
    }

    public void run() {
        String hostName = "localhost";
        int portNumber = 15695;
        String response;
        try {
            this.pensar();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try (Socket echoSocket = new Socket(hostName, portNumber);
             PrintStream pstream = new PrintStream(echoSocket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));) {
            while (true) {
                for (String msg : msgs) {

                    // if(msgs[m].split(" ")[0].equals("release")){
                    // System.out.println("send liberar");
                    // sendReleaseMsg(msgs[m], pstream, in);
                    // }
                    // if(msgs[m].split(" ")[0].equals("acquire")){
                    // System.out.println("send adquirir");
                    // sendAcquireMsg(msgs[m], pstream, in);
                    // }
                    pstream.println(msg);
                    response = in.readLine();
                    System.out.println("echo " + this.toString() + ": " + response);
                    if (response.split(" ").length > 2) {
                        if (response.split(" ")[0].equals("true") && response.split(" ")[2].equals("Ac") && response.split(" ")[3].equals("Platão")) {
                            platao++;
                            if (platao == 2) {
                                this.comer(1);
                                platao = 0;
                                pstream.println("release 1 Platão");
                                pstream.println("release 2 Platão");
                                pstream.println("finish 0 Platão");
                                pensar();
                                System.out.println("-Platão:Vou pensar um pouco");
                            }

                        } else if (response.split(" ")[0].equals("true") && response.split(" ")[3].equals("Socrates")) {
                            socrates++;
                            if (socrates == 2) {
                                this.comer(2);
                                socrates = 0;
                                pstream.println("release 2 Socrates");
                                pstream.println("release 3 Socrates");
                                pstream.println("finish 0 Socrates");
                            }

                        } else if (response.split(" ")[0].equals("true") && response.split(" ")[3].equals("Descartes")) {
                            descartes++;
                            if (descartes == 2) {
                                this.comer(3);
                                descartes = 0;
                                pstream.println("release 3 Descartes");
                                pstream.println("release 4 Descartes");
                                pstream.println("finish 0 Descartes");
                            }

                        } else if (response.split(" ")[0].equals("true") && response.split(" ")[3].equals("Aristoteles")) {
                            aristoteles++;
                            if (aristoteles == 2) {
                                this.comer(4);
                                aristoteles = 0;
                                pstream.println("release 4 Aristoteles");
                                pstream.println("release 5 Aristoteles");
                                pstream.println("finish 0 Aristoteles");
                            }

                        } else if (response.split(" ")[0].equals("true") && response.split(" ")[3].equals("Tales")) {
                            tales++;
                            if (tales == 2) {
                                this.comer(5);
                                tales = 0;
                                pstream.println("release 5 Tales");
                                pstream.println("release 1 Tales");
                                pstream.println("finish 0 Tales");
                            }

                        }
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}