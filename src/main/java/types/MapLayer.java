package types;

import java.util.List;

/**
 * A DTO for map layer by tiled convention.
 */
public class MapLayer {
    public List<Integer> data;
    public List<MapLayerObject> objects;
    public Integer height;
    public Integer id;
    public String name;
    public String type;
    public boolean visible;
    public Integer width;
    public Integer x;
    public Integer y;
}
