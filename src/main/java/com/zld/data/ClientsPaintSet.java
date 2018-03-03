package com.zld.data;

import java.util.*;

public class ClientsPaintSet {
    ClientsSet _0;
    ClientsSet _1;
    public ClientsPaintSet() {
        this._0 = new ClientsSet();
        this._1 = new ClientsSet();
    }
    public ClientsPaintSet(ClientsSet glossy, ClientsSet matte) {
        Objects.requireNonNull(glossy);
        Objects.requireNonNull(matte);
        this._0 = glossy;
        this._1 = matte;
    }
    public ClientsPaintSet(List<Client> glossy, List<Client> matte) {
        Objects.requireNonNull(glossy);
        Objects.requireNonNull(matte);
        this._0 = new ClientsSet(glossy);
        this._1 = new ClientsSet(matte);
    }

    public Set<Client> getClients() {
        final Set<Client> result = new HashSet<>();
        result.addAll(_0.clients);
        result.addAll(_1.clients);
        return result;
    }

    public ClientsSet _0() {
        return _0;
    }

    public ClientsSet _1() {
        return _1;
    }


}
