package mypackage;
public class Place
{	
	private int currentAmountOfTokens;
	
	public String name;
	
	public Place(String name, int currentAmountOfTokens)
	{
		this.currentAmountOfTokens = currentAmountOfTokens;
		this.name = name;
	}
	
	public Place(String name)
	{
		this.currentAmountOfTokens = 0;
		this.name = name;
	}
	
	public int getAmountOfTokens( )
	{
		return this.currentAmountOfTokens;
	}
	
	public void setAmountOfToken(int curAmount){
		currentAmountOfTokens = curAmount;
	}
	
	public void consumeToken( )
	{
		--this.currentAmountOfTokens;
	}
	
	
	public void addToken( )
	{
		++this.currentAmountOfTokens;
	}
	
	
	public void produceToken( )
	{
		++this.currentAmountOfTokens;
	}
	
	
}
