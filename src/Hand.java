import java.util.Random;


public class Hand {

	public Card[] cards;
	public int id;
	
	Random rand;
	
	int tempPos;
	
	public Hand(int id)
	{
		this.id = id;
		cards = new Card[5];
		
		for(int i = 0; i < 5; i++)
			cards[i] = null;
	}

	/*
	public void Hand(int numCards)
	{
		rand = new Random();
		
		for(int i = 0; i < numCards; i++)
		{
			cards[i] = new Card(rand.nextInt((12) + 1), rand.nextInt((3) + 1));
		}
	}
	
	*/
	
	public boolean placeCard(Card C)
	{
		for(int i = 0; i < 5; i++)
		{
			if(cards[i] == null)
			{
				cards[i] = C;
				tempPos = i;
				return true;
			}
		}
		
		System.out.println("Hand " + id + " is full.");
		return false;
	}
	
	public void removeCard(int index)
	{
		cards[tempPos] = null;
	}
	
	public int getNumCards()
	{
		int numCards = 0;
		for(int i = 0; i < 5; i++)
		{
			if(cards[i] != null)
				numCards++;
		}
		
		return numCards;
	}
	
	public void printHand()
	{
		for(Card C: cards)
		{
			if(C != null)
				System.out.print(C + ", ");
		}

		System.out.println("\n---------");
	}
	
	public int highestCard()
	{
		int highest = 0;
		for(Card C : cards)
		{
			if(C != null)
			{
				if(C.getRank() > highest)
				{
					highest = C.getRank();
				}
			}
		}
		
		return highest;
	}
	
	public String toString()
	{
		String output = "Hand " + id + ": [";
		
		for(int i = 0; i < 4; i++)
		{
			if(cards[i] != null)
			{
				output += cards[i].toString() + ", ";
			}
			else
			{
				output += "-,";
			}
		}
		
		if(cards[4] != null)
		{
			output += cards[4].toString() + "]";
		}
		else
		{
			output += "-]";
		}
		
		return output;
		
		
	}
}
