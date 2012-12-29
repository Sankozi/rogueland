package org.sankozi.rogueland.gui.utils;

import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
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
