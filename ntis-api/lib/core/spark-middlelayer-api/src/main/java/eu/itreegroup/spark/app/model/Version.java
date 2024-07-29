package eu.itreegroup.spark.app.model;

public class Version {

    private String branch;

    private String buildNumber;

    private String buildTimestamp;

    public Version(String branch, String buildNumber, String buildTimestamp) {
        super();
        this.branch = branch;
        this.buildNumber = buildNumber;
        this.buildTimestamp = buildTimestamp;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getBuildTimestamp() {
        return buildTimestamp;
    }

    public void setBuildTimestamp(String buildTimestamp) {
        this.buildTimestamp = buildTimestamp;
    }

    @Override
    public String toString() {
        return "Version [branch=" + branch + ", buildNumber=" + buildNumber + ", buildTimestamp=" + buildTimestamp + "]";
    }
}
