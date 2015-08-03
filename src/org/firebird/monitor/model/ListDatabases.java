
package org.firebird.monitor.model;

import java.util.ArrayList;
import java.util.List;

public class ListDatabases {

    private List<Databases> databases = new ArrayList<Databases>();

    public void addDatabase(Databases database) {
        this.databases.add(database);
    }

    public List<Databases> getDatabases() {
        return databases;
    }
}
