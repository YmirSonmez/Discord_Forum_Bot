package forum.modules.debug;

public enum ErrorCodes {
    NUMBER_ERROR(1,"Yalnızca rakamlardan oluşabilir!","Parametreye rakamlar dışında değer girdiğinizde oluşur."),
    USAGE_ERROR(2,"Hatalı komut kullanımı!","Komudu yanlış kullandığınızda oluşur."),
    CHECK_ERROR(3,"Eşleşen bulunamadı!","Girdiğiniz parametre ile eşleşen bir şey bulunamadığında oluşur.");


    private final int code;
    private final String title;
    private final String defaultDescription;

    ErrorCodes(int code, String title, String defaultDescription) {
        this.code = code;
        this.title = title;
        this.defaultDescription = defaultDescription;
    }

    private int getCodeNumber(){return this.code;}
    public String getTitle(){return "#"+getCodeNumber()+" "+this.title;}
    public String getDefaultDescription(){return this.defaultDescription;}
}
