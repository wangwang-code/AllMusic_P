package Color_yr.AllMusic.MusicPlay.SendInfo;

import Color_yr.AllMusic.AllMusic;
import Color_yr.AllMusic.MusicAPI.SongInfo.SongInfo;
import Color_yr.AllMusic.MusicAPI.SongLyric.ShowOBJ;
import Color_yr.AllMusic.MusicPlay.PlayMusic;
import Color_yr.AllMusic.Utils.Function;
import com.google.gson.Gson;

public class SendInfo {
    public static PosOBJ SetPot(String player, String pos, String x, String y) {
        SaveOBJ obj = AllMusic.getConfig().getInfoSave(player);
        if (obj == null)
            obj = new SaveOBJ();
        Pos pos1 = Pos.valueOf(pos);
        PosOBJ posOBJ = new PosOBJ(0, 0);
        if (!Function.isInteger(x) && !Function.isInteger(y))
            return null;
        int x1 = Integer.parseInt(x);
        int y1 = Integer.parseInt(y);

        switch (pos1) {
            case lyric:
                posOBJ = obj.getLyric();
                break;
            case list:
                posOBJ = obj.getList();
                break;
            case info:
                posOBJ = obj.getInfo();
                break;
        }
        posOBJ.setX(x1);
        posOBJ.setY(y1);
        switch (pos1) {
            case lyric:
                obj.setLyric(posOBJ);
                break;
            case list:
                obj.setList(posOBJ);
                break;
            case info:
                obj.setInfo(posOBJ);
                break;
        }

        AllMusic.getConfig().setInfoSave(obj, player);
        AllMusic.save();
        SendInfo.SendSave(player);
        return posOBJ;
    }

    public static void SendListData() {
        StringBuilder list = new StringBuilder();
        if (PlayMusic.getSize() == 0) {
            list.append("队列中无歌曲");
        } else {
            String now;
            for (SongInfo info : PlayMusic.getList()) {
                now = info.getInfo();
                if (now.length() > 30)
                    now = now.substring(0, 29) + "...";
                list.append(now).append("\n");
            }
        }

        if (AllMusic.Side.SendList(list.toString())) {
            AllMusic.save();
        }
    }

    public static void SendNowData() {
        StringBuilder list = new StringBuilder();
        if (PlayMusic.NowPlayMusic == null) {
            list.append("没有播放的音乐");
        } else {
            list.append(PlayMusic.NowPlayMusic.getName()).append("\n");
            list.append(PlayMusic.NowPlayMusic.getAuthor()).append("\n");
            list.append(PlayMusic.NowPlayMusic.getAlia()).append("\n");
            list.append(PlayMusic.NowPlayMusic.getAl()).append("\n");
            list.append("by:").append(PlayMusic.NowPlayMusic.getCall());
        }

        if (AllMusic.Side.SendInfo(list.toString())) {
            AllMusic.save();
        }
    }

    public static void SendLyricData(ShowOBJ showobj) {
        StringBuilder list = new StringBuilder();
        if (showobj == null) {
            list.append("无歌词");
        } else {
            if (showobj.getLyric() != null)
                list.append(showobj.getLyric()).append("\n");
            if (showobj.isHaveT() && showobj.getTlyric() != null)
                list.append(showobj.getTlyric());
        }

        if (AllMusic.Side.SendLyric(list.toString())) {
            AllMusic.save();
        }
    }

    public static boolean SetEnable(String player, String pos) {
        SaveOBJ obj = AllMusic.getConfig().getInfoSave(player);
        if (obj == null)
            obj = new SaveOBJ();

        if (pos == null) {
            obj.setEnableInfo(true);
            obj.setEnableList(true);
            obj.setEnableLyric(true);
        } else {
            Pos pos1 = Pos.valueOf(pos);
            switch (pos1) {
                case info:
                    obj.setEnableInfo(!obj.isEnableInfo());
                    break;
                case list:
                    obj.setEnableList(!obj.isEnableList());
                    break;
                case lyric:
                    obj.setEnableLyric(!obj.isEnableLyric());
                    break;
            }
        }
        clear(player);
        AllMusic.getConfig().setInfoSave(obj, player);
        AllMusic.save();
        if (pos == null) {
            return true;
        } else {
            Pos pos1 = Pos.valueOf(pos);
            switch (pos1) {
                case info:
                    return obj.isEnableInfo();
                case list:
                    return obj.isEnableList();
                case lyric:
                    return obj.isEnableLyric();
            }
        }
        SendInfo.SendSave(player);
        return false;
    }

    public static void clear() {
        AllMusic.Side.ClearAll();
    }

    public static void clear(String Name) {
        AllMusic.Side.Clear(Name);
    }

    public static void SendSave(String Name) {
        try {
            SaveOBJ obj = AllMusic.getConfig().getInfoSave(Name);
            if (obj == null) {
                obj = new SaveOBJ();
                AllMusic.getConfig().setInfoSave(obj, Name);
                AllMusic.save();
            }
            String data = new Gson().toJson(obj);
            AllMusic.Side.Send(data, Name, null);
        } catch (Exception e1) {
            AllMusic.log.warning("§d[AllMusic]§c数据发送发生错误");
            e1.printStackTrace();
        }
    }
}
