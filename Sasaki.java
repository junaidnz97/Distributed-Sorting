/*
Created a class named pair to store detalils of a single node
*/
class pair {
    public int first, second, area;
    public int markedfirst, markedsecond;
}

/*
An extension of the thread class to run each thread and do the processing
*/
class Runimpl extends Thread {
    private int i, j;

    public Runimpl(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /*
    Function to receive the updated value if at all updated
     */
    void receive(pair p) {
        Sasaki.p[i] = p;
    }

    /*
      The function where local computation happens.This is where the swapping happens
     */
    void localcomputation(pair p, int j) {
        if (p.second > Sasaki.p[j].first) {
            if (p.markedsecond == 1) {
                Sasaki.p[j].area -= 1;
            }
            if (Sasaki.p[j].markedfirst == 1) {
                Sasaki.p[j].area += 1;
            }
            int tempval = Sasaki.p[j].first;
            Sasaki.p[j].first = p.second;
            p.second = tempval;
            int tempmarked = p.markedsecond;
            p.markedsecond = Sasaki.p[j].markedfirst;
            Sasaki.p[j].markedfirst = tempmarked;
        }
        /*
        Calling the receive function to update node value
         */
        receive(p);
    }
    /*
    Calling of the send function
     */
    void send(pair p, int j) {

        localcomputation(p, j);
    }

    public void run() {
        try {

            send(Sasaki.p[i], j);
            //Sasaki.flag += 1;
        } catch (Exception e) {
            System.out.println("Exception is caught");
            System.out.println(e);
        }
    }
}

class Sasaki {
    public static int flag = 0;
    public static int n=5;
    public static int arr[] = new int[n];
    public static pair p[] = new pair[n];

    public static void main(String[] args) {
        /*
        Initializing all the array and the node randomly
        */
        for (int i = 0; i < n; i++) {
            arr[i] = (int) ((Math.random() * ((1000 - 1) + 1)) + 1);
            p[i] = new pair();
            p[i].first = arr[i];
            p[i].second = arr[i];
            p[i].area = 0;
            p[i].markedfirst = 0;
            p[i].markedsecond = 0;
        }
        p[0].area = -1;
        p[0].markedsecond = 1;
        p[n - 1].markedfirst = 1;
        /*
        Printing the initial values in the node
         */
        System.out.println("After Round" + 0);
        for (int j = 0; j < n; j++) {
            String markedfirst="";
            String markesecond="";
            if(p[j].markedfirst==1)
                markedfirst="*";
            if(p[j].markedsecond==1)
                markesecond="*";
            if (j == 0)
                System.out.print("|" + p[j].second +markesecond +" (area = " + p[j].area + ")|\t");
            else if (j == n - 1)
                System.out.println("|" + p[j].first + markedfirst+" (area = " + p[j].area + ")|\n");
            else
                System.out.print("|" + p[j].first + markedfirst+"," + p[j].second + markesecond+" (area = " + p[j].area + ")|\t");
        }

        for (int i = 0; i < n - 1; i++) {
            flag = 0;
            Runimpl r[] = new Runimpl[n];
            for (int j = 0; j < n - 1; j++) {
                /*
                Creating a thread object between for communication
                between 2 nodes and running the thread.
                 */
                r[j] = new Runimpl(j, j + 1);
                r[j].start();
            }
            for (int j = 0; j < n - 1; j++) {
                try {
                    /*
                    Waiting for each thread to finish,inorder to complete the round
                     */
                    r[j].join();
                } catch (Exception e) {
                    System.out.println("Exception caught " + e);
                }
            }
            /*
            Further local computation inside the nodes
             */
            for (int j = 1; j < n - 1; j++) {
                if (p[j].first > p[j].second) {
                    int temp = p[j].first;
                    p[j].first = p[j].second;
                    p[j].second = temp;
                    temp = p[j].markedfirst;
                    p[j].markedfirst = p[j].markedsecond;
                    p[j].markedsecond = temp;
                }
            }
            /*
            Printing states of nodes after each round
             */
            System.out.println("After Round" + (i + 1));
            for (int j = 0; j < n; j++) {
                String markedfirst="";
                String markesecond="";
                if(p[j].markedfirst==1)
                    markedfirst="*";
                if(p[j].markedsecond==1)
                    markesecond="*";
                if (j == 0)
                    System.out.print("|" + p[j].second +markesecond +" (area = " + p[j].area + ")|\t");
                else if (j == n - 1)
                    System.out.println("|" + p[j].first + markedfirst+" (area = " + p[j].area + ")|\n");
                else
                    System.out.print("|" + p[j].first + markedfirst+"," + p[j].second + markesecond+" (area = " + p[j].area + ")|\t");
            }
        }
        System.out.println("After Sorting");
        for (int i = 0; i < n; i++) {
            if (i == 0)
                System.out.print(p[i].second + " ");
            else if (i == n - 1)
                System.out.println(p[i].first + "\n");
            else if (p[i].area < 0)
                System.out.print(p[i].second + " ");
            else
                System.out.print(p[i].first + " ");
        }
    }
}