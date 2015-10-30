public class Place
{	
	private int currentAmountOfTokens;
	
	public Place(int currentAmountOfTokens)
	{
		this.currentAmountOfTokens = currentAmountOfTokens;
	}
	
	public int getAmountOfTokens( )
	{
		return this.currentAmountOfTokens;
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
