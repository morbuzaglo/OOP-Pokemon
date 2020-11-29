package ex2;

public class GeoLoc implements geo_location
{
	double x,y,z;
	
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

	public GeoLoc(double x, double y, double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
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

	public void setCoord(geo_location p)
	{
		setX(p.x());
		setY(p.y());
		setZ(p.z());
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public double z() {
		return z;
	}

	@Override
	public String toString()
	{
		return ("Coordinates: (" + x + ", " + y + ", " + z + ").");
	}

	@Override
	public double distance(geo_location g)
	{
		return Math.sqrt(Math.pow(g.x()-x, 2)+Math.pow(g.y()-y, 2)+Math.pow(g.z()-z, 2));
	}
}
