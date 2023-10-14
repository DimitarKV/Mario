package types;

import com.google.gson.annotations.SerializedName;

public class MapLayerObject {
    @SerializedName("gid")
    public Integer GId;
    public Integer height;
    @SerializedName("id")
    public Integer Id;
    public String name;
    public Integer rotation;
    public String type;
    public boolean visible;
    public Integer width;
    public Integer x;
    public Integer y;
}
