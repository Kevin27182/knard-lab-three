# Lab 3 - Data Visualization

This program provides a graphical user interface for filtering and visualizing numeric 
data. The entry point of the program is the `public static void main(String[] args)` 
method within `Main.java`. By default, the program allows the user to work with a 
truncated version of the UCI red wine quality data set.

Source: https://archive.ics.uci.edu/dataset/186/wine+quality

# Lab 4 - Design Patterns

For this portion of the lab, the Observer and Decorator design patterns were implemented.

* **Observer:** The `StatsPanel`, `DetailsPanel`, and `ChartPanel` were converted to 
Observers that update whenever the table in `TablePanel` is updated. In this example, 
the `TablePanel` class is the publisher and the `StatsPanel`, `DetailsPanel`, and 
`ChartPanel` classes are the subscribers. The subscribers now implement the 
`TableObserver` interface and contain the method `onTableUpdateHandler(TableObserverData data)`
that defines custom behavior that operates on the data stored within the `ActionEvent`.
`TableObserverData` is a new data structure that has a `dataDisplay` field that holds the
display data, and a `details` field that holds the details for the `DetailsPanel`.
Each subscriber was added to the `TablePanel` as a `TableObserver`, and whenever the
table is updated, `TablePanel` sends an update to all subscribers.
* **Decorator:** The `StatsPanel` class was converted to a Decorator for `DetailsPanel`.
Instead of extending `DetailsPanel`, `StatsPanel` now *has* a `DetailsPanel` field and
acts as a `DetailsPanel` wrapper class. The methods in `StatsPanel` now operate on the
`DetailsPanel` instance variable. This change removed the strict dependency between the 
two classes by converting the relationship from inheritance to an aggregation.