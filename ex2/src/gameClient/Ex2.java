package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.plaf.TableHeaderUI;
import java.util.List;
import java.util.Random;


public class Ex2 implements Runnable
{
    private int scenario_num = 11;
    private int ID = 318670403;
    private  MyFrame _win;
    private game_service _game;
    private Arena _ar;
    public double LastMove;
    private static GUI gui;
    public static boolean isRunning;

    public static void main(String[] a)
    {
        GUI _gui = new GUI();
        gui = _gui;

        int ind = 0;

        while(!gui.getStartGame())
        {
            System.out.println(gui.getStartGame());
        }
        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run()
    {
        int scenario_num = gui.get_level();
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        int id = gui.get_id();
        //game.login(id);

        this._game = game;
        init(); // arena settings, placing agents and pokemons.
        playing(); // start the game, moving agents decisions.
    }

    private void init()
    {
        Arena ar = new Arena(this._game);
        this._ar = ar;

        MyFrame win = new MyFrame("test Ex2");
        win.setSize(1000, 700); // initial size.
        win.update(ar);
        this._win = win;

        _win.show();
        AgentsFirstSet();
    }

    private void playing()
    {
        _game.startGame();
        _win.setTitle("Ex2 Pokemon Game - scenario: " + this.scenario_num);
        int ind = 0;
        long dt = 100; // MAX moves frequency !

        _ar.addNewAgents(_game.move());

        moveAgents(this._ar);

        for(int i = 0; i < _ar.getAgents().size(); i++)
        {
            Thread th = new Thread( _ar.getAgents().get(i));
            th.start();
        }

        long minTime = Long.MAX_VALUE;
        while(_game.isRunning())
        {
            moveAgents(_ar);

            for(int i = 0; i < _ar.getAgents().size(); i++)
            {
                if(_ar.getAgents().get(i).get_sg_dt() < minTime)
                {
                    minTime = _ar.getAgents().get(i).get_sg_dt();
                }
            }

            try
            {
                if(ind%1 == 0)
                {
                    _win.repaint();
                }
                Thread.sleep((100 < minTime) ? minTime : 100);
                ind++;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        String res = _game.toString();

        System.out.println(res);
        System.exit(0);
    }

             /* * * * * * * * * * * * * * * DECISION FUNCTIONS * * * * * * * * * * * * * * * * * * */

    private void AgentsFirstSet()
    {
        String info = _game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int numOfAgents = ttt.getInt("agents");

            List<CL_Pokemon> poks = _ar.getPokemons();

            for(int i = 0 ; i < poks.size() ; i++)
            {
                _ar.updateEdge(poks.get(i), _ar.getGraph());
            }

            Random rand = new Random(1);

            for(int i = 0 ; i < numOfAgents ; i++)
            {
                CL_Pokemon pok = poks.get(rand.nextInt(poks.size()));

                int startNode = pok.get_edge().getSrc();

                _game.addAgent(startNode);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void moveAgents(Arena _ar)  // TODO implement moveAgents!
    {
        try
        {
            game_service game = _ar.getGame();

            String lg = game.move();

            _ar.updateAgents(lg);

            String fs = game.getPokemons();
            _ar.updatePokemons(fs);
        }
        catch (Exception e)
        {
                return;
        }
    }
}

