import java.util.Collection;
import java.util.LinkedList;

enum KeyStatus {ON_TREE, BORROWED, OBSOLETE};

public class Key {
    private int number;
    private KeyStatus status;
    private Enclosure enclosure;
    private String borrowedBy;
    // public Getters and setters
    public int getNumber(){ return number; }
    public KeyStatus getStatus(){ return status; }
    public void setStatus(KeyStatus status){ this.status = status; }
    public Enclosure getEnclosure(){ return enclosure; }
    public String getBorrowedBy(){ return borrowedBy; }
    public void setBorrowedBy(String username){ this.borrowedBy = username; }
}

class Enclosure {
    private String name;
    private Collection<Key> keys;
    // public getters and setters   
    public String getName(){ return name; }
    public Collection<Key> getKeys(){ return keys; }
}

class KeyInventory {
    private Collection<Key> keys;
    private Collection<Enclosure> enclosures;

    public void initFromDatabase(){
        // loads keys and enclosures from database
        keys = new LinkedList<Key>();
        enclosures = new LinkedList<Enclosure>();
    }

    public void saveToDatabase(){
        // saves any changes to keys or enclosures to the database.
    }

    /**
     * Finds one key for the given enclosure and borrows it to the user
     * @param username
     * @param enclosureName
     */
    public void borrowKeyForEnclosure(String username, String enclosureName) throws Exception {
        // TODO - find a key for the given enclosure and borrow it to the user
        var encFind = enclosures.stream()
                    .filter(e -> e.getName() == enclosureName)
                    .findFirst();
        if(!encFind.isPresent())
            throw new Exception("Enclosure not found");
        var enclosure = encFind.get();
        // find one that's not borrowed
        var keyFind = enclosure.getKeys().stream()
                    .filter(k -> k.getStatus() == KeyStatus.ON_TREE)
                    .findFirst();
        if(!keyFind.isPresent())
            throw new Exception("There's no available keys for that enclosure");
        var key = keyFind.get();
        // set as borrowed
        key.setStatus(KeyStatus.BORROWED);
        key.setBorrowedBy(username);
        saveToDatabase();
    }
}
