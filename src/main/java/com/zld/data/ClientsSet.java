package com.zld.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClientsSet {
    final Set<Client> clients = new HashSet<>();

    ClientsSet() { }

    ClientsSet(Collection<Client> clients) {
        this.clients.addAll(clients);
    }

    ClientsSet(ClientsSet set) {
        this.clients.addAll(set.clients);
    }

    public Set<Client> getClients() {
        return new HashSet<>(clients);
    }
}
