package gameClient;

import api.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class AgentThread implements Runnable
{
    private Arena ar;
    private CL_Agent agent;

    public AgentThread(Arena ar, CL_Agent agent)
    {
        this.ar = ar;
        this.agent = agent;
    }

    @Override
    public void run()
    {
        while(ar.getGame().isRunning())
        {

        }
    }

    private void moveAgents(game_service game, directed_weighted_graph gg)  // TODO implement moveAgents!
    {

        String lg = game.move();
        _ar.updateAgents(lg, gg);
        List<CL_Agent> log = _ar.getAgents();

        String fs =  game.getPokemons();
        _ar.updatePokemons(fs);
        List<CL_Pokemon> ffs = _ar.getPokemons();

        for(int i = 0; i < log.size(); i++)
        {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();

            if(dest == -1)
            {
                dest = nextNode(gg, src);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }

    private static int nextNode(directed_weighted_graph g, int src) // TODO implement nextNode!
    {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
        ans = itr.next().getDest();
        return ans;
    }
}
