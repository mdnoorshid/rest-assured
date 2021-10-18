package com.restassured.service;

import com.restassured.model.Game;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class GameService {

    private static final List<Game> GAMES = Arrays.asList(
            new Game("cheese eater", 299.0d,12),
            new Game("Space Shooter", 209.0d,10),
            new Game("Worm hunter", 300.0d,0),
            new Game("Fishing Game", 33.0d,5)
    );

    public Observable<Game> gameForSale(){
        return Observable.create(emitter -> {
        int i = 0 ;
        System.out.println("Start sending the games...");
        while(!emitter.isDisposed() && i<GAMES.size()){
          Game game = GAMES.get(i);
          if(game.getStrorage() == 0 ){
             emitter.onError(new RuntimeException("Game is not available in the stock: "+game));
          }
          emitter.onNext(game);
            i++;
        }
        System.out.println("Done sending Games!!");
        emitter.onComplete();
        });
    }

}
