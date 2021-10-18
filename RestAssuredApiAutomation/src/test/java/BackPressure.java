import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicInteger;

public class BackPressure {

    @Test
    public void createProducerAndConsumerUsingObservable() {
        Observable.range(1, 100000)
                .map(e -> {
                    sleep(100);
                    System.out.println("Produced Item is: " + e + " : " + Thread.currentThread().getName());
                    return e;
                })
                .observeOn(Schedulers.io()) //it will split afterward work in different thread
                .subscribe(e -> {
                    sleep(1000);
                    System.out.println("Consumed Item is: " + e + " : " + Thread.currentThread().getName());
                });
        sleep(100000);

    }

    @Test
    public void createProducerAndConsumerUsingFlowable() {
        Flowable.range(1, 100000)
                .map(e -> {
                  //  sleep(100);
                    System.out.println("Produced Item is: " + e + " : " + Thread.currentThread().getName());
                    return e;
                })
                .observeOn(Schedulers.io()) //it will split afterward work in different thread
                .subscribe(e -> {
                 //   sleep(1000);
                    System.out.println("Consumed Item is: " + e + " : " + Thread.currentThread().getName());
                });
    //    sleep(100000);

    }

    @Test
    public void createProducerAndConsumerUsingFlowableAndSubscriber() {
        Flowable.range(1, 100000)
                .map(e -> {
                    //  sleep(100);
                    System.out.println("Produced Item is: " + e + " : " + Thread.currentThread().getName());
                    return e;
                })
                .observeOn(Schedulers.io()) //it will split afterward work in different thread
                .subscribe(new Subscriber<Integer>() {
                    Subscription s;
                    AtomicInteger count = new AtomicInteger(0);
                    @Override
                    public void onSubscribe(Subscription subscription) {
                      this.s = subscription;
                      if(count.getAndIncrement() % 20 == 0){
                          s.request(20);
                      }
                      sleep(20);
                    }
                    @Override
                    public void onNext(Integer integer) {
                        if(count.getAndIncrement() % 20 == 0){
                            System.out.println("Asking for next 20 items");
                            s.request(20);
                        }
                        System.out.println("The Subscriber consumed:: "+integer);
                        sleep(100);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                       throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Completed!!");
                    }
                });
        //    sleep(100000);

    }

    @Test
    public void createFlowableWithBackpressureStrategy(){
        Flowable.create(emitter -> {
           for(int i = 0 ; i < 5000 ;i++){
               if(emitter.isCancelled()) return;
               emitter.onNext(i);
           }
        }, BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.io())
                .subscribe(System.out::println);
        sleep(1000);
    }

    private void sleep(int milliSeconds) {
        {
            try {
                Thread.sleep(milliSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
