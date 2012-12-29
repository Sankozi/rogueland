package org.sankozi.rogueland.gui.utils;

import com.google.common.base.Throwables;
import com.google.inject.Provider;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * Provider that is executed inside Event Dispatch Thread
 * @author sankozi
 */
public final class EDTProvider<T> implements Provider<T>{

    private final Callable<T> provider;

    public EDTProvider(Callable<T> provider) {
        this.provider = provider;
    }

    @Override
    public T get() {
        try {
            if(SwingUtilities.isEventDispatchThread()){
                return provider.call();
            }
            FutureTask<T> task = new FutureTask<>(provider);
            SwingUtilities.invokeAndWait(task);
            return task.get();
        } catch (Exception ex){
            throw Throwables.propagate(ex);
        }
    }
}
