package com.ea.eadp.player.service;

import com.ea.eadp.player.entity.Product;
import com.ea.eadp.player.model.Player;
import com.ea.eadp.player.model.PlayerServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlayerService implements PlayerServiceInter {

    private Hashtable<Long, Player> playerHashtable = new Hashtable<>();
    private static final String BASE = "http://localhost:8080/product";
    private RestTemplate restTemplate = new RestTemplate();
    private static final AtomicLong counter = new AtomicLong();


    @Autowired
    public PlayerService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
        initDatabase(new Player("1", "Kaybee", "888552222", "Amataer"));
        initDatabase(new Player("2", "Bapat", "8885524222", "Expert"));

    }

    private void initDatabase(Player player) {
        String userId = UUID.randomUUID().toString();
        player.setUserId(userId);
        playerHashtable.put(counter.incrementAndGet(), player);
    }

    @Override
    public Hashtable<Long, Player> getAllPlayers() {
        return playerHashtable;
    }

    @Override
    public Player getPlayerById(Long id) {
        Player p = playerHashtable.get(id);
        return p;
    }

    @Override
    public Player addPlayer(Player playerDetails) {
        Player p = playerDetails;
        p.setUserId(UUID.randomUUID().toString());
        playerHashtable.put(counter.incrementAndGet(), p);
        return p;
    }

    @Override
    public Player updatePlayer(Player p, Player ep) {
        if (p.getName() != null) ep.setName(p.getName());
        if (p.getContact() != null) ep.setContact(p.getContact());
        if (p.getLevel() != null) ep.setLevel(p.getLevel());
        return ep;
    }

    @Override
    public void deletePlayer(Long id) {
        playerHashtable.remove(id);
    }

    @Override
    public Player associateProduct(Long productId, Long playerId) {
        String url = BASE + "/grant/" + productId;
        Product product = restTemplate.getForObject(url, Product.class);
        Player p = playerHashtable.get(playerId);
        p.addProduct(product);
        return p;
    }

    @Override
    public List<Product> productsOwnedBy(Long id) {
        Player player = playerHashtable.get(id);
        return player.getProductList();
    }


}
