package Color_yr.AllMusic.MusicAPI.SongInfo;

import Color_yr.AllMusic.AllMusic;

public class SongInfo {
    private String Author;
    private String Name;
    private String ID;
    private String Alia;
    private String Call;
    private String Al;
    private String PlayerUrl;

    private int Length;

    private boolean isList;

    public SongInfo(String Name, String Url, int Length) {
        this.Length = Length;
        PlayerUrl = Url;
        this.Name = Name;
        ID = Alia = Call = Al = Author = "";
        isList = false;
    }

    public String getPlayerUrl() {
        return PlayerUrl;
    }

    public SongInfo(String Author, String Name, String ID, String Alia, String Call, String Al, boolean isList, int Length) {
        this.Author = Author;
        this.Name = Name;
        this.ID = ID;
        this.Alia = Alia;
        this.Call = Call;
        this.Al = Al;

        this.isList = isList;
        this.Length = Length;
    }

    public String getAl() {
        return Al;
    }

    public String getAlia() {
        return Alia;
    }

    public String getCall() {
        return Call;
    }

    public String getAuthor() {
        return Author;
    }

    public int getLength() {
        return Length;
    }

    public boolean isList() {
        return isList;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public String getInfo() {
        String info = AllMusic.getMessage().getMusicPlay().getMusicInfo();
        info = info.replace("%MusicName%", Name)
                .replace("%MusicAuthor%", Author)
                .replace("%MusicAl%", Al)
                .replace("%MusicAlia%", Alia)
                .replace("%PlayerName%", Call);
        return info;
    }

    public boolean isNull() {
        return Name == null;
    }
}
