package com.vadrin.mmtstrain.mmtsservices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class StationNameToIDService {

	private static final Map<String, String> namIDMap = new HashMap<String, String>();

	public StationNameToIDService() {
		super();
		namIDMap.put("aler", "167");
		namIDMap.put("alwal", "123");
		namIDMap.put("ammuguda", "124");
		namIDMap.put("arts college", "125");
		namIDMap.put("begumpet", "126");
		namIDMap.put("bharatnagar", "127");
		namIDMap.put("bhongir", "168");
		namIDMap.put("bibinagar jn", "169");
		namIDMap.put("bolarum", "128");
		namIDMap.put("bolarum bazar", "122");
		namIDMap.put("borabanda", "129");
		namIDMap.put("budvel", "130");
		namIDMap.put("cavalry barracks", "131");
		namIDMap.put("chandanagar", "166");
		namIDMap.put("charlapalli", "170");
		namIDMap.put("dabilpur", "133");
		namIDMap.put("dabirpura", "134");
		namIDMap.put("dayanandnagar", "135");
		namIDMap.put("falaknuma", "136");
		namIDMap.put("fatehnagar", "137");
		namIDMap.put("ghanapur", "171");
		namIDMap.put("ghatkesar", "172");
		namIDMap.put("gowdavalli", "138");
		namIDMap.put("gundla pochampalli", "139");
		namIDMap.put("hafeezpet", "140");
		namIDMap.put("hitech city", "141");
		namIDMap.put("huppuguda", "142");
		namIDMap.put("hyderabad", "143");
		namIDMap.put("jamai osmania", "144");
		namIDMap.put("james street", "145");
		namIDMap.put("jangaon", "173");
		namIDMap.put("kacheguda", "146");
		namIDMap.put("kazipet jn", "174");
		namIDMap.put("khairatabad", "147");
		namIDMap.put("kuchavaram h", "175");
		namIDMap.put("lakdikapul", "148");
		namIDMap.put("lallaguda gate", "149");
		namIDMap.put("lingampalli", "150");
		namIDMap.put("malakpet", "151");
		namIDMap.put("malkajgiri", "152");
		namIDMap.put("manoharabad", "153");
		namIDMap.put("medchal", "154");
		namIDMap.put("nature cure hospital", "155");
		namIDMap.put("necklace road", "156");
		namIDMap.put("npa shivrampalli", "157");
		namIDMap.put("pembarthi", "176");
		namIDMap.put("pindial", "177");
		namIDMap.put("raghunathapalli", "178");
		namIDMap.put("raigir", "179");
		namIDMap.put("ramakistapuram gate", "158");
		namIDMap.put("safilguda", "159");
		namIDMap.put("sanjeevaiah park", "160");
		namIDMap.put("secunderabad", "161");
		namIDMap.put("sitafalmandi", "162");
		namIDMap.put("umdanagar", "163");
		namIDMap.put("vidyanagar", "164");
		namIDMap.put("wangapalli", "180");
		namIDMap.put("warangal", "181");
		namIDMap.put("yakutpura", "165");
	}
	
	public String getID(String stationName){
		return namIDMap.get(stationName);
	}
}
