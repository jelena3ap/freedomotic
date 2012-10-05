package it.freedomotic.objects;

import it.freedomotic.model.ds.Config;
import it.freedomotic.model.object.BooleanBehavior;

/**
 *
 * @author Enrico
 */
public class BooleanBehaviorLogic implements BehaviorLogic {

    private final BooleanBehavior data;
    private Listener listener;
    private boolean changed;

    public interface Listener {

        public void onTrue(Config params, boolean fireCommand);

        public void onFalse(Config params, boolean fireCommand);
    }

    public BooleanBehaviorLogic(BooleanBehavior pojo) {
        this.data = pojo;
    }

    @Override
    public synchronized final void filterParams(final Config params, boolean fireCommand) {
        //filter accepted values
        String value = params.getProperty("value").trim();
        if (value.equalsIgnoreCase("false") || value.equals("0")) {
            if (this.getValue() != false) { //if is really changed
                listener.onFalse(params, fireCommand);
            }
        }
        if (value.equalsIgnoreCase("true") || value.equals("1")) {
            if (this.getValue() != true) { //if is really changed
                listener.onTrue(params, fireCommand);
            }
        }

        if (value.equalsIgnoreCase(VALUE_OPPOSITE)) {
            opposite(params, fireCommand);
        }
        if (value.equalsIgnoreCase(VALUE_NEXT)) {
            opposite(params, fireCommand);
        }
        if (value.equalsIgnoreCase(VALUE_PREVIOUS)) {
            opposite(params, fireCommand);
        }
    }

    private void opposite(Config params, boolean fireCommand) {
        if (data.getValue() == true) {
            if (this.getValue() != false) { //if is really changed
                listener.onFalse(params, fireCommand);
            }
        } else {
            if (this.getValue() != true) { //if is really changed
                listener.onTrue(params, fireCommand);
            }
        }
    }

    public void addListener(Listener booleanBehaviorListener) {
        listener = booleanBehaviorListener;
    }

    @Override
    public String getName() {
        return data.getName();
    }

    public void setValue(boolean b) {
        if (data.getValue() != b) {
            data.setValue(b);
            setChanged(true);
        }
    }

    public boolean getValue() {
        return data.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BooleanBehaviorLogic other = (BooleanBehaviorLogic) obj;
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean isActive() {
        return data.isActive();
    }

    @Override
    public String getValueAsString() {
        return data.toString();
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public void setChanged(boolean value) {
        changed = value;
    }
}
