package com.iavariav.monitoringiot.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel{

	@SerializedName("curah_hujan")
	private String curahHujan;

	@SerializedName("id_data")
	private String idData;

	@SerializedName("pergeseran")
	private String pergeseran;

	@SerializedName("kemiringan")
	private String kemiringan;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("kadar_air")
	private String kadarAir;

	@SerializedName("status")
	private String status;

	public void setCurahHujan(String curahHujan){
		this.curahHujan = curahHujan;
	}

	public String getCurahHujan(){
		return curahHujan;
	}

	public void setIdData(String idData){
		this.idData = idData;
	}

	public String getIdData(){
		return idData;
	}

	public void setPergeseran(String pergeseran){
		this.pergeseran = pergeseran;
	}

	public String getPergeseran(){
		return pergeseran;
	}

	public void setKemiringan(String kemiringan){
		this.kemiringan = kemiringan;
	}

	public String getKemiringan(){
		return kemiringan;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setKadarAir(String kadarAir){
		this.kadarAir = kadarAir;
	}

	public String getKadarAir(){
		return kadarAir;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}