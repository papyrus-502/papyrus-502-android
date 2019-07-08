package dto;

public class WordDTO {
    private String originName;
    private String targetName;

    /**
     * Key name.  Usually we remember it.
     * @return
     */
    public String getOriginName() {
        return originName;
    }

    /**
     * Value name.  We need to get it.
     * @param originName
     */
    public void setOriginName(String originName) {
        this.originName = originName;
    }
}
