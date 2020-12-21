package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CL_PokemonTest {

    @Test
    void getID()
    {
        game_service game = Game_Server_Ex2.getServer(0);
        game.startGame();
        Arena ar = new Arena(game);
        ar.updatePokemons(game.getPokemons());
        CL_Pokemon poki=ar.getPokemons().get(0);
        boolean answer=poki.getID()==0;
        assertTrue(answer);
    }

    @Test
    void getValue()
    {
        game_service game = Game_Server_Ex2.getServer(0);
        game.startGame();
        Arena ar = new Arena(game);
        ar.updatePokemons(game.getPokemons());
        CL_Pokemon poki=ar.getPokemons().get(0);
        boolean answer=poki.getValue()==5;
        assertTrue(answer);
    }
    @Test
    void getType()
    {
        game_service game = Game_Server_Ex2.getServer(0);
        game.startGame();
        Arena ar = new Arena(game);
        ar.updatePokemons(game.getPokemons());
        CL_Pokemon poki=ar.getPokemons().get(0);
        boolean answer=poki.getType()==-1;
        assertTrue(answer);
    }
}