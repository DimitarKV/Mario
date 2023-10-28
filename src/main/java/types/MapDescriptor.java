package types;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * The main DTO for map description by tiled convention.
 */
public class MapDescriptor {
    public Integer height;
    public Integer width;
    @SerializedName("tileheight")
    public Integer tileHeight;
    @SerializedName("tilewidth")
    public Integer tileWidth;
    @SerializedName("layers")
    public List<MapLayer> mapLayers;
    @SerializedName("nextlayerid")
    public Integer nextLayerId;
    @SerializedName("nextobjectid")
    public Integer nextObjectId;
    public String orientation;
    @SerializedName("tilesets")
    public List<TilesetEntry> tileSets;
}
