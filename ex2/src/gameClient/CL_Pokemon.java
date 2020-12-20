package gameClient;
import api.edge_data;
import gameClient.util.Point3D;

import javax.swing.*;
import java.util.ArrayList;

public class CL_Pokemon
{
	private edge_data _edge;
	private int id = 0;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	private ImageIcon poky = new ImageIcon("data\\Cartoon-Free-PNG-Image.png");

	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e)
	{
		_type = t;
	//	_speed = s;     // when there will be speed to the pokemons.
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
		this.id = this.id++;
	}

	public String toString()
	{
		return "F:{v="+_value+", t="+_type+"}";
	}

	public edge_data get_edge()
	{
		return _edge;
	}

	public void set_edge(edge_data _edge)
	{
		this._edge = _edge;
	}

	public int getID()
	{
		return this.id;
	}

	public Point3D getLocation()
	{
		return _pos;
	}

	//	public void setLocation(Point3D p) {this._pos = p;}

	public int getType()
	{
		return _type;
	}

	//	public double getSpeed() {return _speed;}

	public double getValue()
	{
		return _value;
	}

	public double getMin_dist()
	{
		return min_dist;
	}

	public void setMin_dist(double mid_dist)
	{
		this.min_dist = mid_dist;
	}

	public int getMin_ro()
	{
		return min_ro;
	}

	public void setMin_ro(int min_ro)
	{
		this.min_ro = min_ro;
	}

	public ImageIcon get_image()
	{
		return poky;
	}
}
