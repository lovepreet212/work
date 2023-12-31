package ca.jrvs.apps.twitter.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coordinates",
        "type"
})
public class Coordinates {
    @JsonProperty("coordinates")
    private float[] coordinates;
    @JsonProperty("type")
    private String type;

    @JsonProperty
    public float[] getCoordinates() {
        return coordinates;
    }

    @JsonProperty
    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    @JsonProperty
    public void setType(String type) {
        this.type = type;
    }

}
