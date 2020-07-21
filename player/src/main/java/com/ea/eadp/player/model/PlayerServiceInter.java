package com.ea.eadp.player.model;

import com.ea.eadp.player.entity.Product;

import java.util.Hashtable;
import java.util.List;

public interface PlayerServiceInter {
    Hashtable<Long, Player> getAllPlayers();

    Player getPlayerById(Long id);

    Player addPlayer(Player playerDetails);

    Player updatePlayer(Player p, Player ep);

    void deletePlayer(Long id);

    Player associateProduct(Long productId, Long playerId);

    List<Product> productsOwnedBy(Long id);
}
