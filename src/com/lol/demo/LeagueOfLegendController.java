package com.lol.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsplumb.common.logger.Log;

@Controller
public class LeagueOfLegendController {
	//API LIST 
	private final String SUMMONER_NAME	= "https://kr.api.pvp.net/api/lol/kr/v1.4/summoner/by-name/";
	private final String GAME_HISTORY 	= "https://kr.api.pvp.net/api/lol/kr/v1.3/game/by-summoner/#ID#/recent";
	
	private String getStringFromInputStream(InputStream is) {
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
	
	@RequestMapping(value = { "/lol/championsList.do" })
	public ModelAndView champonsList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Log.Debug("==== GET CHAMPIONS LIST DATA START ====");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
//		String resutlApiStr = InitializeLolData.getHttpsFreeCall("champion");
//		Log.Debug( resutlApiStr);
//		PrintWriter pw = response.getWriter();
//
//		// WRITE
//		pw.write(resutlApiStr);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("champlist", InitializeLolData.getChampionMap());
		
		Log.Debug("==== GET CHAMPIONS LIST DATA END ====");
		return mav;
	}
	
	@RequestMapping(value = { "/lol/summonerNames.do" })
	public void summonerNames(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Log.Debug("==== GET SUMMONER NAME DATA START ====");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		String enStr = paramMap.get("summonerName").toString().replaceAll(" ","%20"); 
  		String resutlApiStr = InitializeLolData.getHttpsFreeCall(SUMMONER_NAME+enStr);
		Log.Debug( resutlApiStr);
		PrintWriter pw = response.getWriter();

		// WRITE
		pw.write(resutlApiStr);
		
		Log.Debug("==== GET SUMMONER NAME DATA END ====");
	}
	@RequestMapping(value = { "/lol/summonerId.do" })
	public void summonerId(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		Log.Debug("==== GET SUMMONER ID DATA START ====");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		String enStr = paramMap.get("name").toString().replaceAll(" ","%20"); 
  		String resutlApiStr = InitializeLolData.getHttpsFreeCall(SUMMONER_NAME+enStr);
		
  		JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(resutlApiStr);
        JSONObject jsonObject2 = (JSONObject) jsonObject.get(paramMap.get("name").toString());
        Log.Debug(jsonObject2.get("id").toString());
		// WRITE
		Log.Debug("==== GET SUMMONER ID DATA END ====");
	}
	
	public String getSummonerId(String id) throws IOException, ParseException{
		Log.Debug("==== getSummonerId START ====");
		String enStr = id.replaceAll(" ","%20"); 
  		String resutlApiStr = InitializeLolData.getHttpsFreeCall(SUMMONER_NAME+enStr);
		
  		JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(resutlApiStr);
        JSONObject jsonObject2 = (JSONObject) jsonObject.get(id);
        Log.Debug(jsonObject2.get("id").toString());
		// WRITE
		Log.Debug("==== getSummonerId END ====");
		return jsonObject2.get("id").toString();
	}
	
	@RequestMapping(value = { "/lol/gameHistory.do" })
	public void gameHistory(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		Log.Debug("==== GET GAME HISTORY DATA START ====");
		String enStr = paramMap.get("summonerName").toString().replaceAll(" ","%20"); 
		String id = getSummonerId(enStr);
		String resutlApiStr = InitializeLolData.getHttpsFreeCall(GAME_HISTORY.replaceAll("#ID#", id));
		
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter pw = response.getWriter();
		pw.write(resutlApiStr);
        Log.Debug(resutlApiStr);
		Log.Debug("==== GET GAME HISTORY DATA END ====");
	}
}
