import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RxJavaPractice {

    String result = "";

    @Test
    public void testJustFromObservable(){
        //cold observable
        Observable<String> observable =  Observable.just("test");
        observable.subscribe((s) ->{
            result = s+" stringManipulated";
            System.out.println("Observer1: "+result);
        });
        observable.subscribe(s -> System.out.println("Observer2: "+s));
        //Assert.assertEquals("test",result);
    }

    @Test
    public void testOnNextFromObserver(){
        String[] letters = new String[]{"a","b","c","d","e","f","g"};
        Observable<String> observable = Observable.fromArray(letters).map(String::toUpperCase);
        observable.subscribe(
          i -> result += i, //onNext
         error -> System.out.println("There was an error: "+error.getLocalizedMessage()), //onError
                () -> System.out.println("Emission has been completed , and final result is: "+result) // OnCompleted
        );
        assertEquals("ABCDEFG",result);
    }

    @Test
    public void observerSubscribingObservable(){
        String[] letters = new String[]{"a","b","c","d","e","f","g"};
        Observable<String> observable = Observable.fromArray(letters);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed...."+d.isDisposed());
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("Invoked on next " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Invoked on Error: " + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Invoked on complete");
            }



        };
        observable.subscribe(observer);


    }

   /* @Test
    public void hotObserver(){
        //way to create hot observable
        ConnectableObservable<Integer> connectableObservable = Observable.just(1,2,3,4).publish();
        connectableObservable.subscribe(item -> System.out.println("Observer1 received: "+item));
        connectableObservable.connect();
        connectableObservable.subscribe(item -> System.out.println("Observer2 received: "+item));
    }*/



}
