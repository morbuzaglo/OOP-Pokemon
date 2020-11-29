package ex2;

public class Geolocation implements geo_location {
double x,y,z;
	
	public Geolocation()
	{
		x=y=z=0;
		
	}
	public Geolocation(geo_location p)
	{
		x=p.x();
		y=p.y();
		z=p.z();

		
	}
	public Geolocation(double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;

		
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
	public void setx(double x)
	{
		this.x=x;
		
	}
	public void sety(double y)
	{
		this.y=y;
		
	}
	public void setz(double z)
	{
		this.z=z;
	}
	public void setcord(geo_location p)
	{
		setx(p.x());
		sety(p.y());
		setz(p.z());
		
	}
	@Override
	public String toString()
	{
		return (x+","+y+","+z);
	}
	 

	@Override
	public double distance(geo_location g) {
		return Math.sqrt(Math.pow(g.x()-x, 2)+Math.pow(g.y()-y, 2)+Math.pow(g.z()-z, 2));
	}

}
