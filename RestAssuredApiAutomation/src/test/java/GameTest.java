import com.restassured.model.Game;
import com.restassured.service.GameService;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {

    Disposable disposable;
    @DisplayName("Game for sale!!")
    @Test
    public void gameForTest(){
        GameService gameService = new GameService();
        Observable<Game> gameObservable = gameService.gameForSale();
        gameObservable.subscribe(
               data -> System.out.println("Received the data: "+data),
                error -> System.out.println("Error occur: "+error.getLocalizedMessage()),
                () -> System.out.println("Emission Completed!!")
        );
    }

    @DisplayName("subscribing game using Observer")
    @Test
    public void gameUsingObserver(){
        GameService gameService = new GameService();
        Observable<Game> gameObservable = gameService.gameForSale();
        Observer<Game> observer = new Observer<Game>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
               disposable = d;
            }

            @Override
            public void onNext(@NonNull Game game) {
                if(game.getStrorage() == 0)
                    disposable.dispose();
               System.out.println("On Next: "+game);
            }

            @Override
            public void onError(@NonNull Throwable e) {
               System.out.println("Error observer: "+e);
            }

            @Override
            public void onComplete() {
               System.out.println("Emission completed...");
            }
        };
        gameObservable.subscribe(observer);
        //gameObservable.filter(game -> game.getStrorage() > 0).subscribe(observer);
    }
}
