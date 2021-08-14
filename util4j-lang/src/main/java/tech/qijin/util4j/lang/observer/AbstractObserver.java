package tech.qijin.util4j.lang.observer;

import java.util.Observable;
import java.util.Observer;

public abstract class AbstractObserver<T> implements Observer {
    public abstract void update(T event);

    @Override
    public void update(Observable o, Object arg) {
        update((T) arg);
    }
}
