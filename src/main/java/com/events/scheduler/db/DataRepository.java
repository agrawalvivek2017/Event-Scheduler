package com.events.scheduler.db;

public interface DataRepository<T> {
    void openConnection(String username, String password, String databaseName);
    void closeConnection();
    void performQuery(String query);
    T performQueryWithResult(String query);
    T requestData(String query);
}
