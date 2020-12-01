package ex2;

import java.util.StringTokenizer;

public class GeoLoc implements geo_location
{
	private double x,y,z;

	public GeoLoc()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public GeoLoc(geo_location p)
	{
		x=p.x();
		y=p.y();
		z=p.z();
	}

	public GeoLoc(String s)
	{
		StringTokenizer st = new StringTokenizer(s,",");

		String xx=st.nextToken();
		String yy=st.nextToken();
		String zz=st.nextToken();

		x=Double.parseDouble(xx);
		y=Double.parseDouble(yy);
		z=Double.parseDouble(zz);
	}

	public GeoLoc(double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}

	@Override
	public double x()
	{
		return x;
	}

	@Override
	public double y()
	{
		return y;
	}

	@Override
	public double z()
	{
		return z;
	}

	public void setX(double x)
	{
		this.x=x;
	}

	public void setY(double y)
	{
		this.y=y;
	}

	public void setZ(double z)
	{
		this.z=z;
	}

	public void setcord(geo_location p)
	{
		setX(p.x());
		setY(p.y());
		setZ(p.z());

	}

	@Override
	public String toString()
	{
		return (x + "," + y + "," + z);
	}

	@Override
	public double distance(geo_location g)
	{
		return Math.sqrt(Math.pow(g.x()-x, 2)+Math.pow(g.y()-y, 2)+Math.pow(g.z()-z, 2));
	}
	@Override
	public boolean equals(Object n)
	// equals method for geolocation
	{
		if(n==null)
			return false;
		if(n instanceof geo_location)
		{
			
			if(((GeoLoc) n).x()==x&&((GeoLoc) n).y()==y&&((GeoLoc) n).z()==z)
				return true;
			else return false;
		}
		
	return false;	
	}
	
}