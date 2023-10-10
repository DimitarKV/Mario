package types;

import com.google.gson.annotations.SerializedName;

public class TileSetDescriptor {
    public Integer columns;
    public String image;
    @SerializedName("imageheight")
    public Integer imageHeight;
    @SerializedName("imagewidth")
    public Integer imageWidth;
    public Integer margin;
    public String name;
    public Integer spacing;
    @SerializedName("tilecount")
    public Integer tileCount;
    @SerializedName("tileheight")
    public Integer tileHeight;
    @SerializedName("tilewidth")
    public Integer tileWidth;

}
