import java.util.Scanner;

class node {
    int data;
    int modval;
}

class RunImpl extends Thread {
    int i, j, k;

    public RunImpl(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    int sendandreceive(int i, int j) {
        int min = Math.min(alternatealgorithm.p[i].data, alternatealgorithm.p[j].data);
        if (alternatealgorithm.p[i].data > alternatealgorithm.p[j].data)
            alternatealgorithm.p[j].data = alternatealgorithm.p[i].data;
        return min;
    }

    void sendandreceive(int i, int j, int k) {
        int min = Math.min(Math.min(alternatealgorithm.p[i].data, alternatealgorithm.p[j].data), alternatealgorithm.p[k].data);
        int max = Math.max(Math.max(alternatealgorithm.p[i].data, alternatealgorithm.p[j].data), alternatealgorithm.p[k].data);
        int med = alternatealgorithm.p[i].data + alternatealgorithm.p[j].data + alternatealgorithm.p[k].data - max - min;
        alternatealgorithm.p[i].data = min;
        alternatealgorithm.p[j].data = med;
        alternatealgorithm.p[k].data = max;
    }

    public void run() {
        if (k == -1) {
            alternatealgorithm.p[i].data = sendandreceive(i, j);
        } else {
            sendandreceive(i, j, k);
        }
        alternatealgorithm.flag += 1;
    }
}

public class alternatealgorithm {

    public static int n = 5;
    public static node p[] = new node[n];
    public static int flag = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
            p[i] = new node();
            p[i].data = arr[i];
            p[i].modval = i % 3;
        }
        System.out.println("After Round " + (0));
        for (int j = 0; j < n; j++) {
            System.out.print("| " + p[j].data + "," + p[j].modval + "|\t");
        }
        System.out.println();
        for (int i = 0; i < n - 1; i++) {
            flag = 0;
            int num_threads = 0;
            RunImpl r[] = new RunImpl[n];
            for (int j = 0; j < n; j++) {
                if (p[j].modval == 1) {
                    if (j == 0 & j + 1 < n) {
                        //RunImpl runimpl=new RunImpl(j,j+1,-1);
                        //Thread r=new Thread(runimpl);
                        //r.start();
                        r[num_threads] = new RunImpl(j, j + 1, -1);
                        r[num_threads].start();
                        num_threads += 1;
                    } else if (j < n - 1) {
                        //RunImpl runimpl=new RunImpl(j-1,j,j+1);
                        //Thread r=new Thread(runimpl);
                        //r.start();
                        r[num_threads] = new RunImpl(j - 1, j, j + 1);
                        r[num_threads].start();
                        num_threads += 1;
                    } else {
                        //RunImpl runimpl=new RunImpl(j-1,j,-1);
                        //Thread r=new Thread(runimpl);
                        //r.start();
                        r[num_threads] = new RunImpl(j - 1, j, -1);
                        r[num_threads].start();
                        num_threads += 1;
                    }
                }
            }
            /*while(flag<num_threads)
            {
                ;
            }*/
            for (int j = 0; j < num_threads; j++) {
                try {
                    r[j].join();
                } catch (Exception e) {
                    System.out.println("Exception Caught");
                }
            }
            for (int j = 0; j < n; j++) {
                p[j].modval = (p[j].modval + 2) % 3;
            }
            System.out.println("After Round " + (i + 1));
            for (int j = 0; j < n; j++) {
                System.out.print("| " + p[j].data + "," + p[j].modval + "|\t");
            }
            System.out.println();
        }
        for (int i = 0; i < n; i++) {
            System.out.println(p[i].data);
        }
    }
}
