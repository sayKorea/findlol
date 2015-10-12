package com.lol.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsplumb.common.logger.Log;


@Controller
public class LeagueOfLegendView {
	@RequestMapping(value={"/lol/championsView.do"})
    public String chanpionsView(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Log.Debug("==== CHAMPIONS UI START ====");
        return "lol/champions";
    }
	@RequestMapping(value={"/lol/itemsView.do"})
    public String itemsView(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Log.Debug("==== ITEMS UI START ====");
        return "lol/items";
    }
	@RequestMapping(value={"/lol/summonerView.do"})
    public String summonerView(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Log.Debug("==== SUMMONER UI START ====");
        return "lol/summoner";
    }
	
	@RequestMapping(value={"/lol/gameMainView.do"})
    public String gameMainView(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Log.Debug("==== GAME HISTORY UI START ====");
        return "lol/gameMain";
    }
	
}
