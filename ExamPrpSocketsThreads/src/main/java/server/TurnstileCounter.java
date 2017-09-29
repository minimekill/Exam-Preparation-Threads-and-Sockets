package server;

public class TurnstileCounter {

    private int counter = 0;

    public synchronized void count() {
        counter++;
    }

    public int getCount() {
        return counter;
    }

}
