package enums;

public enum SanktionenEnum {

	Graue("Graue Liste"),
	Schwarze("Schwarze Liste");
	
	private String anzeigeName;
	
	private SanktionenEnum(String anzeigeName){
	this.anzeigeName = anzeigeName;
}
	
//	GraueListe("Graue Liste",0L),
//	SchwarzeListe("Schwarze Liste",Long.MAX_VALUE);
//	
//	private String anzeigeName;
//	
//	private SanktionenEnum(String anzeigeName,Long sperrdauerInTagen){
//		this.anzeigeName = anzeigeName;
//	}
	
}
