
// Title: TableObserver.java
// Author: Kevin Nard
// Interface that allows an implementing class to act as a Table Observer

package lab3.gui;

public interface TableObserver {

    // Override to define custom behavior on table update
    void onTableUpdateHandler(TableObserverData data);
}
