# Exam-Preparation-Threads-and-Sockets

we use threads whenever multiple task needs to be done simultaneously. In my program if i didnt use threads, only one client could be online at the time cause it cannot let clients do actions and have queury for new clients to enter at the same time. 
Doing gui it is also needed to make sure the gui or the main program doesnt get stuck.

Race conditions occour whenever 2 or more threads tries to access a nonatomic method. Making the method atomic avoids this.

All new clients gets a newly initiallized object that extends thread that are all the same. this thread is the control for the new client.

Deadlocks occour when 2 or more threads takes locks from methods and lock eachother stuck. Ways to handle them is to make the threads only have one lock at the time and set the pattern the threads takes the lock in a path pattern so all threads "walks" the same way.

1)
Program: ExamPrpSocketsThreads
is started from server.ServerStart.java

2) Race conditions in the turnstile.class with the method : count(), if multiple threads start using this method then the count can mess up cause it was not atomic, with synchronized it is.

3) from cmd: telnet 127.0.0.1 80
enter if ur a turnstile or a observer.
If ur a turnstile use count   to simulate a new guest.

4) by entering observer u can monitor the count

5) from the turnstile u can do getcount  and u will get the count from that turnstile.
