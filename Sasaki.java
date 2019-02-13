import java.util.Scanner;

class pair {
    public int first, second, area;
    public int markedfirst, markedsecond;
}

class Runimpl extends Thread {
    private int i, j;

    public Runimpl(int i, int j) {
        this.i = i;
        this.j = j;
    }

    pair sendandreceive(pair p, int j) {
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
        return p;
    }

    public void run() {
        try {
            /*if (Sasaki.p[i].second > Sasaki.p[j].first) {
                if (Sasaki.p[i].markedsecond == 1) {
                    Sasaki.p[j].area -= 1;
                }
                if (Sasaki.p[j].markedfirst == 1) {
                    Sasaki.p[j].area += 1;
                }
                int tempval = Sasaki.p[j].first;
                Sasaki.p[j].first = Sasaki.p[i].second;
                Sasaki.p[i].second = tempval;
                int tempmarked = Sasaki.p[i].markedsecond;
                Sasaki.p[i].markedsecond = Sasaki.p[j].markedfirst;
                Sasaki.p[j].markedfirst = tempmarked;
            }*/
            Sasaki.p[i] = sendandreceive(Sasaki.p[i], j);

            Sasaki.flag += 1;
        } catch (Exception e) {
            System.out.println("Exception is caught");
            System.out.println(e);
        }
    }
}

class Sasaki {
    public static int flag = 0;
    public static int arr[] = new int[5];
    public static pair p[] = new pair[5];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 5;
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
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

        System.out.println("After Round" + 0);
        for (int j = 0; j < n; j++) {
            if (j == 0)
                System.out.print("|" + p[j].second + "|\t");
            else if (j == n - 1)
                System.out.println("|" + p[j].first + "|\n");
            else
                System.out.print("|" + p[j].first + "," + p[j].second + "|\t");
        }

        for (int i = 0; i < n - 1; i++) {
            flag = 0;
            Runimpl r[] = new Runimpl[n];
            for (int j = 0; j < n - 1; j++) {
                //Runimpl runimpl = new Runimpl(j, j + 1);
                //Thread r = new Thread(runimpl);
                r[j] = new Runimpl(j, j + 1);
                r[j].start();
            }
           /* while (flag < 4) {
                System.out.print("");
            }*/
            for (int j = 0; j < n - 1; j++) {
                try {
                    r[j].join();
                } catch (Exception e) {
                    System.out.println("Exception caught " + e);
                }
            }

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
            System.out.println("After Round" + (i + 1));
            for (int j = 0; j < n; j++) {
                if (j == 0)
                    System.out.print("|" + p[j].second + "|\t");
                else if (j == n - 1)
                    System.out.println("|" + p[j].first + "|\n");
                else
                    System.out.print("|" + p[j].first + "," + p[j].second + "|\t");
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
        /*for(int i=0;i<5;i++)
        {
            System.out.println(p[i].first+" "+p[i].second+" "+p[i].area+" "+p[i].markedfirst+" "+p[i].markedsecond);

        }*/
    }
}