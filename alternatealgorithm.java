/*
Created a class named pair to store detalils of a single node
*/
class node {
    int data;
    int modval;
}
/*
An extension of the thread class to run each thread and do the processing
*/
class RunImpl extends Thread {
    int i, j, k;

    public RunImpl(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }
    /*
    Function to receive the updated value if at all updated,
    when 2 processes try to communicate with each other
     */
    void receive(int i, int min) {
        alternatealgorithm.p[i].data = min;
    }
    /*
    Same as above,but when 3 processes try to communicate with each other
     */
    void receive(int i, int k, int min, int max) {
        alternatealgorithm.p[i].data = min;
        alternatealgorithm.p[k].data = max;
    }
    /*
     The function where local computation happens.This is where the swapping happens,when
     2 processes try to communicate with each other
    */
    void localcomputation(int i, int j) {
        int min = Math.min(alternatealgorithm.p[i].data, alternatealgorithm.p[j].data);
        if (alternatealgorithm.p[i].data > alternatealgorithm.p[j].data)
            alternatealgorithm.p[j].data = alternatealgorithm.p[i].data;
        receive(i, min);
    }
    /*
     Same as above,but when 3 processes try to communicate with each other
    */
    void localcomputation(int i, int j, int k) {
        int min = Math.min(Math.min(alternatealgorithm.p[i].data, alternatealgorithm.p[j].data), alternatealgorithm.p[k].data);
        int max = Math.max(Math.max(alternatealgorithm.p[i].data, alternatealgorithm.p[j].data), alternatealgorithm.p[k].data);
        int med = alternatealgorithm.p[i].data + alternatealgorithm.p[j].data + alternatealgorithm.p[k].data - max - min;
        alternatealgorithm.p[i].data = min;
        alternatealgorithm.p[j].data = med;
        alternatealgorithm.p[k].data = max;
    }
    /*
    Send function when 2 processes try to communicate with each other
     */
    void send(int i, int j) {
        localcomputation(i, j);
    }
    /*
    Same as above,but when 3 processes try to communicate with each other
    */
    void send(int i, int j, int k) {
        localcomputation(i, j, k);
    }
    /*
    This is where the thread runs
     */
    public void run() {
        if (k == -1) {
            send(i, j);
        } else {
            send(i, j, k);
        }
        //alternatealgorithm.flag += 1;
    }
}

public class alternatealgorithm {

    public static int n = 5;
    public static node p[] = new node[n];
    public static int flag = 0;
    /*
    Initializing the array with random values
    */
    public static void main(String[] args) {
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) ((Math.random() * ((1000 - 1) + 1)) + 1);
            p[i] = new node();
            p[i].data = arr[i];
            p[i].modval = i % 3;
        }
        /*
        Printing initial values
         */
        System.out.println("After Round " + (0));
        for (int j = 0; j < n; j++) {
            System.out.print("| " + p[j].data + " (Mod_Val= " + p[j].modval + ") |\t");
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < n - 1; i++) {
            flag = 0;
            int num_threads = 0;
            RunImpl r[] = new RunImpl[n];
            for (int j = 0; j < n; j++) {
                if (p[j].modval == 1) {
                    if (j == 0 & j + 1 < n) {
                        r[num_threads] = new RunImpl(j, j + 1, -1);
                        r[num_threads].start();
                        num_threads += 1;
                    } else if (j < n - 1) {
                        r[num_threads] = new RunImpl(j - 1, j, j + 1);
                        r[num_threads].start();
                        num_threads += 1;
                    } else {
                        r[num_threads] = new RunImpl(j - 1, j, -1);
                        r[num_threads].start();
                        num_threads += 1;
                    }
                }
            }
            for (int j = 0; j < num_threads; j++) {
                try {
                    /*
                    Waiting for thread to end
                     */
                    r[j].join();
                } catch (Exception e) {
                    System.out.println("Exception Caught");
                }
            }
            for (int j = 0; j < n; j++) {
                p[j].modval = (p[j].modval + 2) % 3;
            }
            /*
            Printing states after each round
             */
            System.out.println("After Round " + (i + 1));
            for (int j = 0; j < n; j++) {
                System.out.print("| " + p[j].data + " (Mod_Val= " + p[j].modval + ") |\t");
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("After Sorting");
        for (int i = 0; i < n; i++) {
            System.out.print(p[i].data + " ");
        }
    }
}
