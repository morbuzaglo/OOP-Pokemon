package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CL_AgentTest {

    @Test
    void isMoving()
    {

        game_service game = Game_Server_Ex2.getServer(0);
        game.startGame();
        Arena ar = new Arena(game);
        game.addAgent(0);
        ar.addNewAgents(game.getAgents());
        CL_Agent agent=ar.getAgents().get(0);
        boolean answer=agent.isMoving();
        assertFalse(answer);
    }
    @Test
    void setID()
    {

        game_service game = Game_Server_Ex2.getServer(0);
        game.startGame();
        Arena ar = new Arena(game);
        game.addAgent(0);
        ar.addNewAgents(game.getAgents());
        CL_Agent agent=ar.getAgents().get(0);
        agent.setID(1);
        boolean answer=agent.getID()==1;
        assertTrue(answer);
    }
    @Test
    void setSpeed()
    {
        game_service game = Game_Server_Ex2.getServer(0);
        game.startGame();
        Arena ar = new Arena(game);
        game.addAgent(0);
        ar.addNewAgents(game.getAgents());
        CL_Agent agent=ar.getAgents().get(0);
        agent.setSpeed(10);
        boolean answer=agent.getSpeed()==10;
        assertTrue(answer);

    }
}