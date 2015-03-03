import java.util.Arrays;


public class PotentialAnalyzer {

	Hand[] hands;
	boolean[] potArray;
	Card[] cards;

	public PotentialAnalyzer(Hand[] hands)
	{
		this.hands = hands;
		potArray = new boolean[9];
		cards = new Card[5];
		
	}
	
	public PotentialAnalyzer()
	{
		potArray = new boolean[9];
		cards = new Card[5];
	}
	
	public int getPotential(Hand H, Card newCard)
	{
		
		//System.out.println("GET POTENTIAL CALLED");
		int bestHandID = 0;
		int highest = 0;
		int potential;
		int numCards;
		
		if(H.placeCard(newCard))
		{
			//System.out.println(H);
			potential = 0;
			numCards = H.getNumCards();

			//Reset potential hands array
			for(int i = 0; i < 9; i++) 
				potArray[i] = true;

			
			/*Sort cards, putting all null positions to the end. Keep two pointers beginning at opposite ends of the array, working their
			* way towards each other whether the current card is null or not.
			*/
			int i = 0, j = 4;
			for(int x = 0; x < 5; x++)
			{
				if(H.cards[x] == null)
				{
					System.out.println("NULL");
					cards[j] = null;
					j--;
				}
				else
				{
					System.out.println(H.cards[x]);
					cards[i] = H.cards[x];
					i++;
				}
			}
			
			/*
			
			String output = "Hand " + H.id + ": [";
			
			for(int k = 0; k < 4; k++)
			{
				if(cards[k] != null)
				{
					output += cards[k].toString() + ", ";
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
			
			System.out.println(output);
			
			*/
			
			switch(numCards)
			{
				case 1:
					check1();
					break;
				case 2:
					check2();
					break;
				case 3:
					check3();
					break;
				case 4:
					check4();
					break;
				case 5:
					check5();
					break;
			}
			
			/*
			 * HARCODED VALUES FOR AMERICAN. NEED TO BE CHANGED TO ADAPT
			 */
			if(potArray[0]) potential += 2;
			if(potArray[1]) potential += 5;
			if(potArray[2]) potential += 10;
			if(potArray[3]) potential += 15;
			if(potArray[4]) potential += 20;
			if(potArray[5]) potential += 25;
			if(potArray[6]) potential += 50;
			if(potArray[7]) potential += 75;
			if(potArray[8]) potential += 100;
			
			//System.out.println("Hand " + H.id + " has a potential score of " + potential);
			
			
			//Remove the temporary card from the hand
			if(H.getNumCards() != 5)
			{
				H.removeCard(H.tempPos);
				
			}
			
			return potential;
		}
		
		return 0;
	}
	
	/*
	public Hand getHighestPotential(Card newCard)
	{
		int bestHandID = 0;
		int highest = 0;
		int potential;
		int numCards;
		
		int nullCount;
		
		for(Hand H: hands)
		{
			if(H.placeCard(newCard))
			{
				potential = 0;
				numCards = H.getNumCards();
				nullCount = 5 - numCards;
				
				//Reset potential hands array
				for(int i = 0; i < 9; i++) 
					potArray[i] = true;
				
				//Copy over cards from input hand into working hand
				for(int i = 0; i < 5; i++)
				{
					if(H.cards[i] == null)
					{
						cards[i] = null;
					}
					else
					{
						H.cards[i] = inCards[i];
					}
				}
				
				//Sort cards, putting all null positions to the end
				cards = new Card[5];
				for(int i = 4; i >= 0; i--)
				{
					if(nullCount > 0)
					{
						cards[i] = null;
						nullCount--;
					}
					else
					{
						cards[i] = inCards[i];
					}
				}
				
				switch(numCards)
				{
					case 1:
						check1();
						break;
					case 2:
						check2();
						break;
					case 3:
						check3();
						break;
					case 4:
						check4();
						break;
					case 5:
						check5();
						break;
				}
				

				if(potArray[0]) potential += 2;
				if(potArray[1]) potential += 5;
				if(potArray[2]) potential += 10;
				if(potArray[3]) potential += 15;
				if(potArray[4]) potential += 20;
				if(potArray[5]) potential += 25;
				if(potArray[6]) potential += 50;
				if(potArray[7]) potential += 75;
				if(potArray[8]) potential += 100;
				
				System.out.println("Hand " + H.id + " has a potential score of " + potential);
				
				if(potential > highest)
				{
					highest = potential;
					bestHandID = H.id;
				}
			}

		}
		
		for(Hand H: hands)
		{
			if(H.id == bestHandID) 
				return H;
		}
		
		return null;
	}
	
	
	*/
	void check1()
	{
		if(!(cards[0].getRank() == 10 || 
		   cards[0].getRank() == 11 || 
		   cards[0].getRank() == 12 || 
		   cards[0].getRank() == 13 || 
		   cards[0].getRank() == 14))
		{
			potArray[8] = false;
		}
	}
	
	void check2()
	{
		check1();
		if(cards[0].getSuit() != cards[1].getSuit())
		{
			potArray[4] = false;
			potArray[7] = false;
			potArray[8] = false;
		}
		if(Math.abs(cards[0].getRank() - cards[1].getRank()) > 4)
		{
			potArray[3] = false;
			potArray[7] = false;
			potArray[8] = false;
		}
		
		if(cards[0].getRank() == cards[1].getRank())
			potArray[3] = false;
	}
	
	void check3()
	{
		check2();
		if(cards[0].getRank() != cards[1].getRank() && cards[0].getRank() != cards[2].getRank() && cards[1].getRank() != cards[2].getRank())
		{
			potArray[5] = false;
			potArray[6] = false;
		}
		
		for(int i = 0; i < cards.length - 1; i++)
		{
			if(cards[i + 1] != null)
			{
				if(cards[i].getSuit() != cards[i + 1].getSuit())
					potArray[4] = false;
			}
		}
		
		int[] rankArray = {cards[0].getRank(), cards[1].getRank(), cards[2].getRank()};
		Arrays.sort(rankArray);
		
		if(Math.abs(rankArray[0] - rankArray[rankArray.length - 1]) > 4)
		{
			potArray[3] = false;
		}
		
		boolean flag = false;
		
		for( int i = 0 ; i < rankArray.length - 1; i++ )
		{
	      if(rankArray[i] == rankArray[i+1])
	        flag = true;
		}
		
		if(flag)
			potArray[3] = false;
		
	}
	
	void check4()
	{
		
		check3();
		boolean flag = false;
		int[] rankArray = {cards[0].getRank(), cards[1].getRank(), cards[2].getRank(), cards[3].getRank()};
		Arrays.sort(rankArray);
		
		for( int i = 0 ; i < rankArray.length - 1; i++ )
		{
	      if(rankArray[i] == rankArray[i+1])
	        flag = true;
		}

		if(cards[0].getSuit() != cards[1].getSuit() ||
			cards[0].getSuit() != cards[1].getSuit() ||
			cards[0].getSuit() != cards[1].getSuit() ||
			cards[0].getSuit() != cards[1].getSuit())
		{
			potArray[4] = false;
		}
		
		if(!flag)
		{
			potArray[2] = false;
			potArray[1] = false;
		}
		else
		{
			potArray[3] = false;
		}
		
		if(Math.abs(rankArray[0] - rankArray[rankArray.length - 1]) > 4)
		{
			potArray[3] = false;
		}
	}
	
	void check5()
	{
		check4();
		
		boolean flag = false;
		int[] rankArray = {cards[0].getRank(), cards[1].getRank(), cards[2].getRank(), cards[3].getRank(), cards[4].getRank()};
		Arrays.sort(rankArray);
		
		for( int i = 0 ; i < rankArray.length - 1; i++ )
		{
	      if(rankArray[i] == rankArray[i+1])
	        flag = true;
		}
		
		for(int i = 0; i < cards.length - 1; i++)
		{
			if(cards[i].getSuit() != cards[i + 1].getSuit())
				potArray[4] = false;
		}
		
		if(flag)
		{
			potArray[0] = false;
		}
	}
	
	
}
