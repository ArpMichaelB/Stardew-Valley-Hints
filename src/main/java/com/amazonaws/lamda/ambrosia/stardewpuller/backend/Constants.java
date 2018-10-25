package com.amazonaws.lamda.ambrosia.stardewpuller.backend;

public class Constants {
	
	public static final String bundleNoter="Bundle|";
	public static final String bundleSuffix=" bundle";
	public static final String classPrefix="{|class=";
	public static final String closeBrackets="]]";
	public static final String closeBracketsSplit="\\]\\]";
	public static final String closeSquiggles="}}";
	public static final String closeSquigglesSplit="\\}\\}";
	public static final String collapseContentPrefix="|content=";
	public static final String collapseNoter="Collapse|";
	public static final String endOfCurrentContent="}}\n";
	public static final String errorMessage="Unhandled Page Setup";
	public static final String externalLinkSensor="https://";
	public static final String openBracketsSplit="\\[\\[";
	public static final String openingSquigglesSplit="\\{\\{";
	public static final String Pagename="PAGENAME";
	public static final String priceNoter="price|";
	public static final String uppercasePriceNoter="Price|";
	public static final String priceSuffix="gold";
	public static final String redirectMessage="redir\n";
	public static final String redirectSensor="#REDIRECT";
	public static final String startOfNewContent="\n{{";
	public static final String travellingPrice="tprice|";
	public static final String tripleQuote="\'\'\'";
	public static final String variousAmounts="various amounts";
	public static final String wikiLink="https://stardewvalleywiki.com/mediawiki/";
	public static final String nothingFound="I didn't find anything for ";
	private Constants() {
	}
}
