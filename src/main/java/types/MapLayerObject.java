package types;

import com.google.gson.annotations.SerializedName;

/**
 * A DTO for map layer objects by tiled convention.
 */
public class MapLayerObject {
    @SerializedName("gid")
    public Integer gid;
    public Integer height;
    @SerializedName("id")
    public Integer id;
    public String name;
    public Integer rotation;
    public String type;
    public boolean visible;
    public Integer width;
    public Integer x;
    public Integer y;
}
