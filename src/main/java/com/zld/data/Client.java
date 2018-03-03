package com.zld.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    final List<Paint> favorite = new ArrayList<>();
    final int clientId;

    public Client(String line, int clientId) {
        addPaints(line);
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public List<Paint> getFavorite() {
        return new ArrayList<>(favorite);
    }

    private void addPaints(String line) {
        final String[] rawData = line.trim().split(" ");
        final int numberFavoriteColors = Integer.parseInt(rawData[0]);
        for (int i = 1; i <= numberFavoriteColors; i++) {
            favorite.add(new Paint(
                    Integer.parseInt(rawData[2 * i - 1]) - 1,
                    PaintType.fromId(Integer.parseInt(rawData[2 * i]))
            ));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
