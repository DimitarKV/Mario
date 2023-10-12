package types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapDescriptor {
    public Integer height;
    public Integer width;
    @SerializedName("tileheight")
    public Integer tileHeight;
    @SerializedName("tilewidth")
    public Integer tileWidth;
    public List<Layer> layers;
    @SerializedName("nextlayerid")
    public Integer nextLayerId;
    @SerializedName("nextobjectid")
    public Integer nextObjectId;
    public String orientation;
    @SerializedName("tilesets")
    public List<TilesetEntry> tileSets;
}
