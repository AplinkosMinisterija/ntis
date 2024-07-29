package lt.project.ntis.models.api;

import java.util.List;

public class ApiList<T> {

    private List<T> data;

    private ApiLinks links;

    public ApiList(List<T> data, ApiLinks links) {
        super();
        this.data = data;
        this.links = links;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult [data=" + data + ", links=" + links + "]";
    }

    public ApiLinks getLinks() {
        return links;
    }

    public void setLinks(ApiLinks links) {
        this.links = links;
    }

}
