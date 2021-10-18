import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class AsyncCallObservable {


    @Test
    public void simpleAsync() {
        System.out.println("Starting simple async..., Thread name: " + Thread.currentThread().getName());
        Flowable.create(s -> {
            try {
                System.out.println("Executing async flowable, Thread name: " + Thread.currentThread().getName());
                System.out.println("Finished async flowable., Thread name: " + Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.println("Error: " + e.getLocalizedMessage());
            }
            s.onComplete();
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).subscribe(e-> System.out.println("e--->"+e));
    }

}
