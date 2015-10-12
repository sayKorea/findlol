/**
 * 
 */
package com.lol.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsplumb.common.logger.Log;

/**
 * @author Administrator
 * 
 */
public class InitializeLolData implements InitializingBean {
	
	// DEFAULT URL KEY
	private final static String DEFAULT_KEY = "api_key=06468edc-4035-4bda-8715-87b0b980de29";
	private final String DEFAULT_URL = "https://global.api.pvp.net/api/lol/static-data/kr/v1.2/";

	// API LIST
	private final String summonerName 		= "https://kr.api.pvp.net/api/lol/kr/v1.4/summoner/by-name/";
	private final String championsOther 	= "https://kr.api.pvp.net/api/lol/kr/v1.2/champion";

	private final String languageStrings 	= DEFAULT_URL+"language-strings";
	private final String items 				= DEFAULT_URL+"item";
	private final String champions 			= DEFAULT_URL+"champion";
	private final String summonerSpell 		= DEFAULT_URL+"summoner-spell";
	private final String rune 				= DEFAULT_URL+"rune";
	private final String mastery 			= DEFAULT_URL+"mastery";
	
	// Initialize List
	private static ArrayList<Map<String, Object>> FreeChampionList		= new ArrayList<Map<String, Object>>();
	private static ArrayList<Map<String, Object>> NotRankChampionList 	= new ArrayList<Map<String, Object>>();
	private static ArrayList<Map<String, Object>> ActiveChampionList 	= new ArrayList<Map<String, Object>>();
	private static ArrayList<Map<String, Object>> InActiveChampionList 	= new ArrayList<Map<String, Object>>();
	private static ArrayList<Map<String, Object>> AiBotChampionMList 	= new ArrayList<Map<String, Object>>();
	private static ArrayList<Map<String, Object>> CustomBotChampionList = new ArrayList<Map<String, Object>>();
	
	private static Map<String, Map<String, String>> championMap 		= new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> championIdMap 		= new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> itemMap 			= new HashMap<String, Map<String, String>>();
	private static Map<String, String> languageStringMap 				= new HashMap<String, String>();
	private static Map<String, Map<String, String>> summonerSpellMap 	= new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> runeMap 			= new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, Object>> masteryMap 			= new HashMap<String, Map<String, Object>>();
	
	// HTTPS CONNECTION 
	private static HttpsURLConnection httpsCon = null;
	
	private InitializeLolData() throws IOException {
		champonsMap();
		itemMap();
		languageStringMap();
		spellMap();
		runeMap();
		masteryMap();
		champonsOtherList();
	}

	public static HttpsURLConnection getHttpsCon() {
		return httpsCon;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("###### SUCCESS INITIALIZED ##### : ");
	}

	public static String getHttpsFreeCall(String apiurl) throws IOException {
		Log.Debug("==== Initialized " + apiurl + " HTTPS API CALL START ====");
		URL url = new URL(apiurl + "?" + DEFAULT_KEY);
		httpsCon = (HttpsURLConnection) url.openConnection();
		httpsCon.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		httpsCon.connect();

		Log.Debug("==== HTTPS API CALL END ====");
		return getStringFromInputStream(httpsCon.getInputStream());
	}

	public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * JSON TO ARRAYLIST
	 * 
	 * @param jsonString
	 * @return
	 */
	private ArrayList<Map<String, String>> jsonToArrayList(String jsonString) {
		Log.Debug("==== jsonToArrayList START ====");
		ArrayList<Map<String, String>> rtnArrayList = new ArrayList<Map<String, String>>();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject masterObj = (JSONObject) jsonParser.parse(jsonString);
			JSONObject dataObj = (JSONObject) masterObj.get("data");
			Iterator<?> iter = dataObj.keySet().iterator();
			while (iter.hasNext()) {
				String keys = (String) iter.next();
				JSONObject valueObject = (JSONObject) dataObj.get(keys);
				Iterator<?> iter2 = valueObject.keySet().iterator();
				Map<String, String> tempChampionProperty = new HashMap<String, String>();
				while (iter2.hasNext()) {
					String keys2 = (String) iter2.next();
					tempChampionProperty.put(keys2, valueObject.get(keys2).toString());
				}
				rtnArrayList.add(tempChampionProperty);
			}
		} catch (ParseException e) { e.printStackTrace(); }
		Log.Debug("==== jsonToArrayList END ====");
		return rtnArrayList;
	}

	private HashMap<String,Map<String, String>> jsonToHashMap(String jsonString) {
		Log.Debug("==== jsonToHashMap START ====");
		HashMap<String,Map<String, String>> tmpMap = new HashMap<String,Map<String, String>>();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject masterObj = (JSONObject) jsonParser.parse(jsonString);
			JSONObject dataObj = (JSONObject) masterObj.get("data");
			Iterator<?> iter = dataObj.keySet().iterator();
			while (iter.hasNext()) {
				String keys = (String) iter.next();
				JSONObject valueObject = (JSONObject) dataObj.get(keys);
				Iterator<?> iter2 = valueObject.keySet().iterator();
				Map<String, String> tempValueMap = new HashMap<String, String>();
				while (iter2.hasNext()) {
					String keys2 = (String) iter2.next();
					tempValueMap.put(keys2, valueObject.get(keys2).toString());
				}
				tmpMap.put(keys, tempValueMap);
			}
		} catch (ParseException e) { e.printStackTrace(); }
		Log.Debug("==== jsonToHashMap END ====");
		return tmpMap;
	}
	
	private String findChampionName(String id){
		Iterator iter = championMap.keySet().iterator();
		String rtnStr = "";
		while(iter.hasNext()){
			String keys = (String) iter.next();
			if(championMap.get(keys).get("id").toString().equalsIgnoreCase(id)){
				rtnStr =  championMap.get(keys).get("name");
				break;
			};
		}
		return rtnStr;
	}
	
	
	
	/**
	 * Champions List
	 */
	private void champonsMap() throws IOException {
		Log.Debug("==== GET CHAMPIONS MAP DATA START ====");
		championMap = jsonToHashMap(getHttpsFreeCall(champions));
		championMap = new TreeMap<String,Map<String,String>>(championMap);
		
		championIdMap = new HashMap<String,Map<String, String>>();
		
//		Log.Debug("#######  PRINT CHAMPION LIST \n" + championMap.toString());;
		Log.Debug("==== GET CHAMPIONS MAP DATA END ====");
	}
	static class NameAscCompare implements Comparator<Map<String, String>> {
		@Override
		public int compare(Map<String, String> arg0, Map<String, String> arg1) {
			return arg0.get("name").toString().compareTo(arg1.get("name").toString());
		}
	}
	static class NameDescCompare implements Comparator<Map<String, String>> {
		@Override
		public int compare(Map<String, String> arg0, Map<String, String> arg1) {
			return arg1.get("name").toString().compareTo(arg0.get("name").toString());
		}
	}

	
	/**
	 * Champions List
	 */
	private void champonsOtherList() throws IOException {
		Log.Debug("==== GET CHAMPIONS OTHER  MAP DATA START ====");
		String jsonString = getHttpsFreeCall(championsOther);
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject masterObj = (JSONObject) jsonParser.parse(jsonString);
			List<Map> champList = (List<Map>) masterObj.get("champions");
			
			for( Map m : champList){
				m.put("name", findChampionName(m.get("id").toString()));
				if((Boolean) m.get("botMmEnabled")){
					CustomBotChampionList.add(m);
				}
				if((Boolean) m.get("rankedPlayEnabled")){
					NotRankChampionList.add(m);
				}
				if((Boolean) m.get("botEnabled")){
					AiBotChampionMList.add(m);
				}
				if((Boolean) m.get("active")){
					ActiveChampionList.add(m);
				}
				if((Boolean) m.get("freeToPlay")){
					FreeChampionList.add(m);
				}
				if(!(Boolean) m.get("active")){
					InActiveChampionList.add(m);
				}
			}
		} catch (ParseException e) { e.printStackTrace(); }
		Log.Debug("==== GET CHAMPIONS OTHER MAP DATA END ====");
	}

	private ArrayList<Map<String, Object>> extracted(JSONObject masterObj) {
		return (ArrayList<Map<String,Object>>) masterObj.get("champions");
	}
	
	/**
	 * ITEM List
	 * 
	 * @throws IOException
	 */
	private void itemMap() throws IOException {
		Log.Debug("==== GET ITEM MAP DATA START ====");
		itemMap = jsonToHashMap( getHttpsFreeCall(items) );
		
//		Log.Debug(itemMap.get("1038").get("name"));
//		Log.Debug(itemMap.get("3087").get("name"));
//		Log.Debug(itemMap.get("3340").get("name"));
//		Log.Debug(itemMap.get("1055").get("name"));
//        
		Log.Debug("==== GET ITEM MAP DATA END ====");
	}
	
	
	/**
	 * ITEM List
	 * 
	 * @throws IOException
	 */
	private void languageStringMap() throws IOException {
		Log.Debug("==== GET ITEM MAP DATA START ====");
		String jsonString = getHttpsFreeCall(languageStrings);
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject masterObj = (JSONObject) jsonParser.parse(jsonString);
			JSONObject dataObj = (JSONObject) masterObj.get("data");
			Iterator<?> iter = dataObj.keySet().iterator();
			while (iter.hasNext()) {
				String keys = (String) iter.next();
				languageStringMap.put(keys, dataObj.get(keys).toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Log.Debug("==== GET ITEM MAP DATA END ====");
	}
	
	/**
	 * Spell List
	 * 
	 * @throws IOException
	 */
	private void spellMap() throws IOException {
		Log.Debug("==== GET ITEM MAP DATA START ====");
		summonerSpellMap = jsonToHashMap( getHttpsFreeCall(summonerSpell) );
		Log.Debug("==== GET ITEM MAP DATA END ====");
	}
	
	/**
	 * Rune List
	 * 
	 * @throws IOException
	 */
	private void runeMap() throws IOException {
		Log.Debug("==== GET RUNE MAP DATA START ====");
		String jsonString = getHttpsFreeCall(rune);
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject masterObj = (JSONObject) jsonParser.parse(jsonString);
			JSONObject dataObj = (JSONObject) masterObj.get("data");
			Iterator<?> iter = dataObj.keySet().iterator();
			while (iter.hasNext()) {
				String keys = (String) iter.next();
				JSONObject valueObject = (JSONObject) dataObj.get(keys);
				Iterator<?> iter2 = valueObject.keySet().iterator();
				Map<String, String> tempValueMap = new HashMap<String, String>();
				while (iter2.hasNext()) {
					String keys2 = (String) iter2.next();
					if(keys2.equalsIgnoreCase("rune")){
						JSONObject valueObject2 = (JSONObject) valueObject.get(keys2);
						Iterator<?> iter3 = valueObject2.keySet().iterator();
						while (iter3.hasNext()) {
							String keys3 = (String) iter3.next();
							tempValueMap.put(keys3, valueObject2.get(keys3).toString());
						}
					}else{
						tempValueMap.put(keys2, valueObject.get(keys2).toString());
					}
				}
				runeMap.put(keys, tempValueMap);
			}
		} catch (ParseException e) { e.printStackTrace(); }
		Log.Debug("==== GET RUNE MAP DATA END ====");
	}
	
	
	/**
	 * Mastery List
	 * 
	 * @throws IOException
	 */
	private void masteryMap() throws IOException {
		Log.Debug("==== GET MASTERY MAP DATA START ====");
		String jsonString = getHttpsFreeCall(mastery);
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject masterObj = (JSONObject) jsonParser.parse(jsonString);
			JSONObject dataObj = (JSONObject) masterObj.get("data");
			Iterator<?> iter = dataObj.keySet().iterator();
			while (iter.hasNext()) {
				String keys = (String) iter.next();
				JSONObject valueObject = (JSONObject) dataObj.get(keys);
				Iterator<?> iter2 = valueObject.keySet().iterator();
				Map<String, Object> tempValueMap = new HashMap<String, Object>();
				while (iter2.hasNext()) {
					String keys2 = (String) iter2.next();
					if(keys2.equalsIgnoreCase("description")){
						tempValueMap.put(keys2, valueObject.get(keys2));
					}else{
						tempValueMap.put(keys2, valueObject.get(keys2).toString());
					}
				}
				masteryMap.put(keys, tempValueMap);
			}
		} catch (ParseException e) { e.printStackTrace(); }
		Log.Debug("==== GET MASTERY MAP DATA END ====");
	}
	
	

	public static ArrayList<Map<String, Object>> getFreeChampionList() {
		return FreeChampionList;
	}

	public static void setFreeChampionList(ArrayList<Map<String, Object>> freeChampionList) {
		FreeChampionList = freeChampionList;
	}

	public static ArrayList<Map<String, Object>> getNotRankChampionList() {
		return NotRankChampionList;
	}

	public static void setNotRankChampionList(ArrayList<Map<String, Object>> notRankChampionList) {
		NotRankChampionList = notRankChampionList;
	}

	public static ArrayList<Map<String, Object>> getActiveChampionList() {
		return ActiveChampionList;
	}

	public static void setActiveChampionList(ArrayList<Map<String, Object>> activeChampionList) {
		ActiveChampionList = activeChampionList;
	}

	public static ArrayList<Map<String, Object>> getInActiveChampionList() {
		return InActiveChampionList;
	}

	public static void setInActiveChampionList(ArrayList<Map<String, Object>> inActiveChampionList) {
		InActiveChampionList = inActiveChampionList;
	}

	public static ArrayList<Map<String, Object>> getAiBotChampionMList() {
		return AiBotChampionMList;
	}

	public static void setAiBotChampionMList(ArrayList<Map<String, Object>> aiBotChampionMList) {
		AiBotChampionMList = aiBotChampionMList;
	}

	public static ArrayList<Map<String, Object>> getCustomBotChampionList() {
		return CustomBotChampionList;
	}

	public static void setCustomBotChampionList(ArrayList<Map<String, Object>> customBotChampionList) {
		CustomBotChampionList = customBotChampionList;
	}

	public static Map<String, Map<String, String>> getChampionMap() {
		return championMap;
	}

	public static void setChampionMap(Map<String, Map<String, String>> championMap) {
		InitializeLolData.championMap = championMap;
	}

	public static Map<String, Map<String, String>> getItemMap() {
		return itemMap;
	}

	public static void setItemMap(Map<String, Map<String, String>> itemMap) {
		InitializeLolData.itemMap = itemMap;
	}

	public static Map<String, String> getLanguageStringMap() {
		return languageStringMap;
	}

	public static void setLanguageStringMap(Map<String, String> languageStringMap) {
		InitializeLolData.languageStringMap = languageStringMap;
	}

	public static Map<String, Map<String, String>> getSummonerSpellMap() {
		return summonerSpellMap;
	}

	public static void setSummonerSpellMap(Map<String, Map<String, String>> summonerSpellMap) {
		InitializeLolData.summonerSpellMap = summonerSpellMap;
	}

	public static Map<String, Map<String, String>> getRuneMap() {
		return runeMap;
	}

	public static void setRuneMap(Map<String, Map<String, String>> runeMap) {
		InitializeLolData.runeMap = runeMap;
	}

	public static Map<String, Map<String, Object>> getMasteryMap() {
		return masteryMap;
	}

	public static void setMasteryMap(Map<String, Map<String, Object>> masteryMap) {
		InitializeLolData.masteryMap = masteryMap;
	}

	public String getDEFAULT_KEY() {
		return DEFAULT_KEY;
	}

	public static void setHttpsCon(HttpsURLConnection httpsCon) {
		InitializeLolData.httpsCon = httpsCon;
	}
}
