package com.ko.accs2.bean;

import java.util.List;
/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public  class IncubatorParamBean{
	private int temperatureSet;
	private int temperature;
	private int CO2Set;
	private int CO2;
	private int humidity;

	public IncubatorParamBean(){

	}

	public IncubatorParamBean(int temperatureSet, int temperature, int CO2Set, int CO2, int humidity) {
		this.temperatureSet = temperatureSet;
		this.temperature = temperature;
		this.CO2Set = CO2Set;
		this.CO2 = CO2;
		this.humidity = humidity;
	}

	public int getTemperatureSet() {
		return temperatureSet;
	}

	public void setTemperatureSet(int temperatureSet) {
		this.temperatureSet = temperatureSet;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getCO2Set() {
		return CO2Set;
	}

	public void setCO2Set(int CO2Set) {
		this.CO2Set = CO2Set;
	}

	public int getCO2() {
		return CO2;
	}

	public void setCO2(int CO2) {
		this.CO2 = CO2;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "IncubatorParamBean{" + "temperatureSet=" + temperatureSet + ", temperature=" + temperature + ", CO2Set=" + CO2Set + ", CO2=" + CO2 + ", humidity=" + humidity + '}';
	}
}
