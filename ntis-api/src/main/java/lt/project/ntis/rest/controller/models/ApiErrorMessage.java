package lt.project.ntis.rest.controller.models;

public class ApiErrorMessage {

    private String status;

    private String source;

    private String title;

    private String detail;

    public ApiErrorMessage() {
        super();
    }

    public ApiErrorMessage(String status, String source, String title, String detail) {
        super();
        this.status = status;
        this.source = source;
        this.title = title;
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}