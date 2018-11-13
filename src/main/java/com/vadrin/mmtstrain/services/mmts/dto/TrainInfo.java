package com.vadrin.mmtstrain.services.mmts.dto;

public class TrainInfo {

	private String trainname;
	private String startstation;
	private String stopstation;
	private String starttime;
	private String stoptime;
	private String runsonweekend;

	public TrainInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTrainname() {
		return trainname;
	}

	public void setTrainname(String trainname) {
		this.trainname = trainname;
	}

	public String getStartstation() {
		return startstation;
	}

	public void setStartstation(String startstation) {
		this.startstation = startstation;
	}

	public String getStopstation() {
		return stopstation;
	}

	public void setStopstation(String stopstation) {
		this.stopstation = stopstation;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getStoptime() {
		return stoptime;
	}

	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}

	public String getRunsonweekend() {
		return runsonweekend;
	}

	public void setRunsonweekend(String runsonweekend) {
		this.runsonweekend = runsonweekend;
	}

}
