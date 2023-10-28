package types;

import com.google.gson.annotations.SerializedName;

/**
 * A DTO for tileset reading.
 */
public class TilesetEntry {
    @SerializedName("firstgid")
    public Integer gid;
    public String source;
}
