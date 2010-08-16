/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smerty.ham.qrz;

/**
 * 
 * @author paul
 */
public class QrzHamProfile {

	private String call; // <call>AA7BQ</call>
	private String dxcc; // <dxcc>291</dxcc>
	private String fname; // <fname>FRED L</fname>
	private String name; // <name>LLOYD</name>
	private String addr1; // <addr1>1202 W VISTA AVE</addr1>
	private String addr2; // <addr2>PHOENIX</addr2>
	private String state; // <state>AZ</state>
	private String zip; // <zip>85021</zip>
	private String country; // <country>United States</country>
	private String lat; // <lat>33.546438</lat>
	private String lon; // <lon>-112.088227</lon>
	private String grid; // <grid>DM33wn</grid>
	private String county; // <county>Maricopa</county>
	private String ccode; // <ccode>271</ccode>
	private String fips; // <fips>04013</fips>
	private String land; // <land>United States</land>
	private String efdate; // <efdate>2009-10-27</efdate>
	private String expdate; // <expdate>2020-01-20</expdate>
	private String p_call; // <p_call>KJ6RK</p_call>
	private String hamclass; // <class>E</class>
	private String codes; // <codes>HAI</codes>
	private String qslmgr; // <qslmgr>EMAIL ONLY</qslmgr>
	private String email; // <email>fred@qrz.com</email>
	private String url; // <url>http://www.qrz.com/db/aa7bq</url>
	private String u_views; // <u_views>181770</u_views>
	private String bio; // <bio>7013/Tue May 4 17:56:45 2010</bio>
	private String image; // <image>http://www.qrz.com/hampages/q/b/aa7bq/aa7bq.jpg</image>
	private String moddate; // <moddate>2010-05-19 14:58:41</moddate>
	private String MSA; // <MSA>6200</MSA>
	private String AreaCode; // <AreaCode>602</AreaCode>
	private String TimeZone; // <TimeZone>Mountain</TimeZone>
	private String GMTOffset; // <GMTOffset>-7</GMTOffset>
	private String DST; // <DST>N</DST>
	private String eqsl; // <eqsl>0</eqsl>
	private String mqsl; // <mqsl>0</mqsl>
	private String cqzone; // <cqzone>3</cqzone>
	private String ituzone; // <ituzone>6</ituzone>
	private String locref; // <locref>3</locref>
	private String iota; // <iota>NA-169</iota>
	private String lotw; // <lotw>0</lotw>
	private String user; // <user>AA7BQ</user>

	public QrzHamProfile() {
	}

	public String getAreaCode() {
		return AreaCode;
	}

	public void setAreaCode(String AreaCode) {
		this.AreaCode = AreaCode;
	}

	public String getDST() {
		return DST;
	}

	public void setDST(String DST) {
		this.DST = DST;
	}

	public String getGMTOffset() {
		return GMTOffset;
	}

	public void setGMTOffset(String GMTOffset) {
		this.GMTOffset = GMTOffset;
	}

	public String getMSA() {
		return MSA;
	}

	public void setMSA(String MSA) {
		this.MSA = MSA;
	}

	public String getTimeZone() {
		return TimeZone;
	}

	public void setTimeZone(String TimeZone) {
		this.TimeZone = TimeZone;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCqzone() {
		return cqzone;
	}

	public void setCqzone(String cqzone) {
		this.cqzone = cqzone;
	}

	public String getDxcc() {
		return dxcc;
	}

	public void setDxcc(String dxcc) {
		this.dxcc = dxcc;
	}

	public String getEfdate() {
		return efdate;
	}

	public void setEfdate(String efdate) {
		this.efdate = efdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEqsl() {
		return eqsl;
	}

	public void setEqsl(String eqsl) {
		this.eqsl = eqsl;
	}

	public String getExpdate() {
		return expdate;
	}

	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}

	public String getFips() {
		return fips;
	}

	public void setFips(String fips) {
		this.fips = fips;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getHamclass() {
		return hamclass;
	}

	public void setHamclass(String hamclass) {
		this.hamclass = hamclass;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIota() {
		return iota;
	}

	public void setIota(String iota) {
		this.iota = iota;
	}

	public String getItuzone() {
		return ituzone;
	}

	public void setItuzone(String ituzone) {
		this.ituzone = ituzone;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLocref() {
		return locref;
	}

	public void setLocref(String locref) {
		this.locref = locref;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLotw() {
		return lotw;
	}

	public void setLotw(String lotw) {
		this.lotw = lotw;
	}

	public String getModdate() {
		return moddate;
	}

	public void setModdate(String moddate) {
		this.moddate = moddate;
	}

	public String getMqsl() {
		return mqsl;
	}

	public void setMqsl(String mqsl) {
		this.mqsl = mqsl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getP_call() {
		return p_call;
	}

	public void setP_call(String p_call) {
		this.p_call = p_call;
	}

	public String getQslmgr() {
		return qslmgr;
	}

	public void setQslmgr(String qslmgr) {
		this.qslmgr = qslmgr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getU_views() {
		return u_views;
	}

	public void setU_views(String u_views) {
		this.u_views = u_views;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean setProfileField(String fieldName, String fieldValue) {

		if (fieldName.equalsIgnoreCase("call")) {
			this.call = fieldValue;
		} else if (fieldName.equalsIgnoreCase("dxcc")) {
			this.dxcc = fieldValue;
		} else if (fieldName.equalsIgnoreCase("fname")) {
			this.fname = fieldValue;
		} else if (fieldName.equalsIgnoreCase("name")) {
			this.name = fieldValue;
		} else if (fieldName.equalsIgnoreCase("addr1")) {
			this.addr1 = fieldValue;
		} else if (fieldName.equalsIgnoreCase("addr2")) {
			this.addr2 = fieldValue;
		} else if (fieldName.equalsIgnoreCase("state")) {
			this.state = fieldValue;
		} else if (fieldName.equalsIgnoreCase("country")) {
			this.country = fieldValue;
		} else if (fieldName.equalsIgnoreCase("lat")) {
			this.lat = fieldValue;
		} else if (fieldName.equalsIgnoreCase("lon")) {
			this.lon = fieldValue;
		} else if (fieldName.equalsIgnoreCase("grid")) {
			this.grid = fieldValue;
		} else if (fieldName.equalsIgnoreCase("county")) {
			this.county = fieldValue;
		} else if (fieldName.equalsIgnoreCase("ccode")) {
			this.ccode = fieldValue;
		} else if (fieldName.equalsIgnoreCase("fips")) {
			this.fips = fieldValue;
		} else if (fieldName.equalsIgnoreCase("land")) {
			this.land = fieldValue;
		} else if (fieldName.equalsIgnoreCase("efdate")) {
			this.efdate = fieldValue;
		} else if (fieldName.equalsIgnoreCase("expdate")) {
			this.expdate = fieldValue;
		} else if (fieldName.equalsIgnoreCase("class")) {
			this.hamclass = fieldValue;
		} else if (fieldName.equalsIgnoreCase("codes")) {
			this.codes = fieldValue;
		} else if (fieldName.equalsIgnoreCase("email")) {
			this.email = fieldValue;
		} else if (fieldName.equalsIgnoreCase("url")) {
			this.url = fieldValue;
		} else if (fieldName.equalsIgnoreCase("u_views")) {
			this.u_views = fieldValue;
		} else if (fieldName.equalsIgnoreCase("image")) {
			this.image = fieldValue;
		} else if (fieldName.equalsIgnoreCase("moddate")) {
			this.moddate = fieldValue;
		} else if (fieldName.equalsIgnoreCase("MSA")) {
			this.MSA = fieldValue;
		} else if (fieldName.equalsIgnoreCase("AreaCode")) {
			this.AreaCode = fieldValue;
		} else if (fieldName.equalsIgnoreCase("TimeZone")) {
			this.TimeZone = fieldValue;
		} else if (fieldName.equalsIgnoreCase("GMTOffset")) {
			this.GMTOffset = fieldValue;
		} else if (fieldName.equalsIgnoreCase("DST")) {
			this.DST = fieldValue;
		} else if (fieldName.equalsIgnoreCase("eqsl")) {
			this.eqsl = fieldValue;
		} else if (fieldName.equalsIgnoreCase("mqsl")) {
			this.mqsl = fieldValue;
		} else if (fieldName.equalsIgnoreCase("cqzone")) {
			this.cqzone = fieldValue;
		} else if (fieldName.equalsIgnoreCase("ituzone")) {
			this.ituzone = fieldValue;
		} else if (fieldName.equalsIgnoreCase("locref")) {
			this.locref = fieldValue;
		} else if (fieldName.equalsIgnoreCase("lotw")) {
			this.lotw = fieldValue;
		} else if (fieldName.equalsIgnoreCase("user")) {
			this.user = fieldValue;
		} else if (fieldName.equalsIgnoreCase("p_call")) {
			this.p_call = fieldValue;
		} else if (fieldName.equalsIgnoreCase("bio")) {
			this.bio = fieldValue;
		} else {
			return false;
		}
		return true;
	}

}
