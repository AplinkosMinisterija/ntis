package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class CacheStatistics {

    private long size;

    private long sizeBytes;

    private long memorySize;

    private long diskSize;

    private long memorySizeBytes;

    private long diskSizeBytes;

    private long hits;

    private long memoryHits;

    private long diskHits;

    private double percentageHits;

    private String percentageHitsStr;

    private long misses;

    private double percentageMisses;

    private String percentageMissesStr;

    public void calculate() {
        setSize(getMemorySize() + getDiskSize());
        setHits(getMemoryHits() + getDiskHits());
        setSizeBytes(getMemorySizeBytes() + getDiskSizeBytes());
        setPercentageHits((getHits() + getMisses() > 0) ? ((100l * getHits()) / (getHits() + getMisses())) : 0);
        setPercentageMisses((getHits() + getMisses() > 0) ? ((100l * getMisses()) / (getHits() + getMisses())) : 0);
        setPercentageHitsStr(getPercentageHits() != 0 ? "" + getPercentageHits() + " %" : "N/A");
        setPercentageMissesStr(getPercentageMisses() != 0 ? "" + getPercentageMisses() + " %" : "N/A");
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public long getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(long diskSize) {
        this.diskSize = diskSize;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public long getMemoryHits() {
        return memoryHits;
    }

    public void setMemoryHits(long memoryHits) {
        this.memoryHits = memoryHits;
    }

    public long getDiskHits() {
        return diskHits;
    }

    public void setDiskHits(long diskHits) {
        this.diskHits = diskHits;
    }

    public double getPercentageHits() {
        return percentageHits;
    }

    public void setPercentageHits(double percentageHits) {
        this.percentageHits = percentageHits;
    }

    public long getMisses() {
        return misses;
    }

    public void setMisses(long misses) {
        this.misses = misses;
    }

    public double getPercentageMisses() {
        return percentageMisses;
    }

    public void setPercentageMisses(double percentageMisses) {
        this.percentageMisses = percentageMisses;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public long getMemorySizeBytes() {
        return memorySizeBytes;
    }

    public void setMemorySizeBytes(long memorySizeBytes) {
        this.memorySizeBytes = memorySizeBytes;
    }

    public long getDiskSizeBytes() {
        return diskSizeBytes;
    }

    public void setDiskSizeBytes(long diskSizeBytes) {
        this.diskSizeBytes = diskSizeBytes;
    }

    public String getPercentageHitsStr() {
        return percentageHitsStr;
    }

    public void setPercentageHitsStr(String percentageHitsStr) {
        this.percentageHitsStr = percentageHitsStr;
    }

    public String getPercentageMissesStr() {
        return percentageMissesStr;
    }

    public void setPercentageMissesStr(String percentageMissesStr) {
        this.percentageMissesStr = percentageMissesStr;
    }

}