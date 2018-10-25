package com.amazonaws.lamda.ambrosia.stardewpuller.backend;


import java.util.ArrayList;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class WikiPuller {

	public static String getDescription(String item) throws Exception {
		
		MediaWikiBot wiki = new MediaWikiBot(Constants.wikiLink); 
			item = cleanTitle(item);
			String page = wiki.getArticle(item).getText();
			page = processRedirect(page,wiki);
			String description = extractDescription(page);
			description = cleanOrdinaryLinks(description);
			if(description.contains("{")) 
				description = resolveSquigglyLinks(description, item);
			description = cutOutExtras(description);
			if(description.equals(Constants.nothingFound))
			{
				description += item + ", try a different wording for the item?";
			}
			return description; 
		}
	/**
	 * In theory, all we really need to do from the user's input is capitalize the words so mediawikibot can find the query
	 * @param item the title to clean up
	 * @return a cleaned, media-wiki ready title
	 */
	private static String cleanTitle(String item) {
		String temp = "";
		if(item.contains(" "))
		{
			String[] words = item.split(" ");
			for(String s : words)
			{
				temp += s.toUpperCase().charAt(0);
				temp += s.substring(1);
				temp += " ";
			}
		}
		else
		{
			temp += item.toUpperCase().charAt(0);
			temp += item.substring(1);
		}
		return temp;
	}

	/**
	 * Cut out anything that's left after the link removals
	 * @param description the description to remove the extras from
	 * @return the cleaned up description
	 */
	private static String cutOutExtras(String description) 
	{
		if(description.contains(Constants.classPrefix) && description.startsWith(Constants.tripleQuote))
		{
			description = description.substring(0,description.indexOf(Constants.classPrefix)); 
		}
		if(description.contains(Constants.tripleQuote))
		{
			String[] temp = description.split(Constants.tripleQuote);
			description = "";
			for(String s : temp)
			{
				description+=s;
			}
		}
		return description;
	}
	
	/**
	 * Removes the links {{formatted like this|thing}} from a wikitext description
	 * @param description description of the item to remove
	 * @param item name of the item, to put in place of PAGENAME variable
	 * @return the description without the active content links
	 */
	private static String resolveSquigglyLinks(String description, String item) {
		String cleaned = ""; 
		String[] openSquiggleSplit = description.split(Constants.openingSquigglesSplit); 
		ArrayList<String> items = new ArrayList<String>();
		for(String s : openSquiggleSplit)
		{
			if(s.contains(Constants.closeSquiggles)) 
			{
				items.add(s.split(Constants.closeSquigglesSplit)[0]); 
			}
			else
			{
				cleaned += s;
			}
		}
		int i = 1;
		for(String s : items)
		{
			if(s.equalsIgnoreCase(Constants.Pagename)) 
			{
				s=item;
			}
			if(s.contains(Constants.travellingPrice)) 
			{
				s=Constants.variousAmounts; 
				//if it's a travelling cart price range, just say various amounts
			}
			else if(s.contains(Constants.priceNoter) || s.contains(Constants.uppercasePriceNoter)) 
			{
				s = s.substring(s.indexOf("|")+1); 
				s += Constants.priceSuffix; 
				//if it's a price 
			}
			else if(s.contains(Constants.bundleNoter) && s.contains("||")) 
			{
				s = s.substring(s.indexOf("|")+1, s.indexOf("||"));
				//s = the word between Bundle| and ||
				s += Constants.bundleSuffix; 
			}
			cleaned+=s;
			cleaned+=openSquiggleSplit[i].substring(openSquiggleSplit[i].indexOf(Constants.closeSquiggles)+2); 
			i++;
		}
		return cleaned;
	}

	/**
	 * Cleans all the [[Whatnot formatted like this|links]] out of a given wikitext description
	 * @param description the description to remove the links from
	 * @return a version of the description that contains the text, including the 
	 */
	private static String cleanOrdinaryLinks(String description) {
		String cleaned = "";
		String[] openBracketSplit = description.split(Constants.openBracketsSplit); 
		ArrayList<String> items = new ArrayList<String>();
		for(String s : openBracketSplit)
		{
			if(s.contains(Constants.collapseNoter)) 
			{
				s = s.substring(s.indexOf(Constants.collapseContentPrefix)); 
			}
			if(s.contains(Constants.closeBrackets)) 
			{
				items.add(s.split(Constants.closeBracketsSplit)[0]); 
			}
			else
			{
				cleaned += s;
			}
		}
		int i = 1;
		for(String s : items)
		{
			if(s.contains("|")) 
			{
				s = s.substring(s.indexOf("|")+1);
			}
			else if(s.contains(Constants.externalLinkSensor)) 
			{
				s = s.substring(s.indexOf(" ")); 
			}
			cleaned+=s;
			cleaned+=openBracketSplit[i].substring(openBracketSplit[i].indexOf(Constants.closeBrackets)+2); 
			i++;
			
		}
		return cleaned;
	}

	/**
	 * Extracts the initial piece of plaintext information from a wiki page (i.e. the description)
	 * @param page the full text of the page
	 * @return The description of the item
	 */
	private static String extractDescription(String page) {
		String modified;
		if(page.startsWith("[[File:"))
		{
			modified = page.substring(page.indexOf("]]")+2,page.indexOf("=="));
		}
		//if it starts with an image/link, remove that, since it'll break things down the line
		else if(page.contains(Constants.tripleQuote)) 
		{
			int endOfDesc = page.indexOf("=="); 
			if(!page.contains("==")) 
			{
				endOfDesc = page.indexOf(Constants.startOfNewContent); 
			}
			modified = page.substring(page.indexOf(Constants.tripleQuote), endOfDesc); 
		}
		//if it starts with 3 quotes, the description is, odds are, between the opening 3 quotes, and the next ==, which marks the next heading
		else if(page.contains(Constants.endOfCurrentContent)) 
		{
			int endOfDesc = page.indexOf("=="); 
			if(!page.contains("==")) 
			{
				endOfDesc = page.indexOf(Constants.startOfNewContent); 
			}

			//if it doesn't have a next heading in it
			//then we pull the description from between the opening infobox and the following infobox
			modified = page.substring(page.indexOf(Constants.endOfCurrentContent)+Constants.endOfCurrentContent.length(),endOfDesc);
		}
		//if it doesn't start with 3 quotes and contains this string, it probably starts with an infobox
		//so take the thing between the infobox and the next heading
		else if(page.isEmpty())
		{
			modified = Constants.nothingFound;
		}
		else
		{
			System.err.println(Constants.errorMessage); 
			modified = page;
		}
		if(modified.startsWith("]]"))
		{
			modified = modified.substring(2);
		}
		//if it starts with ]], we missed removing a set of close brackets on an open image
		//and they need to get removed to make things not break later
		return modified;
	}

	private static String processRedirect(String pageName, MediaWikiBot wiki)
	{
		while(pageName.contains(Constants.redirectSensor)) 
		{
			System.out.println(Constants.redirectMessage); 
			pageName = pageName.substring(pageName.lastIndexOf("[")+1, pageName.indexOf("]")); 
			pageName = wiki.getArticle(pageName).getText();
		}
		return pageName;
	}

}
