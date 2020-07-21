package com.ea.eadp.player.controller;

import com.ea.eadp.player.entity.Product;
import com.ea.eadp.player.model.Player;
import com.ea.eadp.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService ps;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public Hashtable<Long, Player> getAllPLayers() {
        return ps.getAllPlayers();
    }

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public Player getPlayerById(@PathVariable("id") Long id) {
        return ps.getPlayerById(id);
    }

    @GetMapping(value = "/{productId}/{playerId}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public Player addProducts(@PathVariable("productId") Long productId, @PathVariable("playerId") Long playerId) {
        return ps.associateProduct(productId, playerId);
    }

    @GetMapping(value = "/owned/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public List<Product> productsOwned(@PathVariable("id") Long id) {
        return ps.productsOwnedBy(id);
    }

    @PostMapping(value = "/add",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public Player addPlayer(@RequestBody Player playerDetails) {
        return ps.addPlayer(playerDetails);
    }

    @PostMapping(value = "/update/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public Player updatePlayer(@RequestBody Player playerDetails, @PathVariable("id") Long id) {
        Player ep = ps.getPlayerById(id);
        return ps.updatePlayer(playerDetails, ep);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable("id") Long id) {
        ps.deletePlayer(id);
    }


}
