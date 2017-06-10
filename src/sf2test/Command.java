package sf2test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leonardo
 */
public class Command {

    public static class Key {
        
        public int keyCode;
        public long pressedTime;
        
        public Key(int keyCode) {
            this.keyCode = keyCode;
            this.pressedTime = System.currentTimeMillis();
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() - pressedTime > 500;
        }
        
    }
    
    private final List<Key> keys = new ArrayList<Key>();
    private final Map<String, int[]> programs = new HashMap<String, int[]>();

    public void addKey(int keyCode) {
        keys.add(new Key(keyCode)); // TODO: get key object from cache ?
    }
    
    public void clear() {
        keys.clear();
    }

    public void clearExpired() {
        Iterator<Key> i = keys.iterator();
        while (i.hasNext()) {
            Key key = i.next();
            if (key.isExpired()) {
                i.remove();
            }
        }
    }
    
    public void program(String name, int ... keys) {
        programs.put(name, keys);
    }
    
    public String check() {
        if (keys.isEmpty()) {
            return "";
        }
        clearExpired();
        // TODO needs to check from larger number of programmed keys
        nextProgram:
        for (String name : programs.keySet()) {
            int[] pkeys = programs.get(name);
            for (int k = 0; k < pkeys.length; k++) {
                if (keys.size() < pkeys.length || pkeys[k] != keys.get(keys.size() - pkeys.length + k).keyCode) {
                    continue nextProgram;
                }
            }
            clear();
            return name;
        }
        return "";
    }

    public void print() {
        System.out.print("cache: ");
        for (Key key : keys) {
            System.out.print(key.keyCode + ", ");
        }
        System.out.println("");
    }
        
}
