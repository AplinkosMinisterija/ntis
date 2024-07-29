package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class CacheInfo {

    private String name;

    private long evictions;

    private long expirations;

    private long gets;

    private double hitPercentage;

    private long hits;

    private long misses;

    private double missPercentage;

    private long puts;

    private long removals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEvictions() {
        return evictions;
    }

    public void setEvictions(long evictions) {
        this.evictions = evictions;
    }

    public long getExpirations() {
        return expirations;
    }

    public void setExpirations(long expirations) {
        this.expirations = expirations;
    }

    public long getGets() {
        return gets;
    }

    public void setGets(long gets) {
        this.gets = gets;
    }

    public double getHitPercentage() {
        return hitPercentage;
    }

    public void setHitPercentage(double hitPercentage) {
        this.hitPercentage = hitPercentage;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public long getMisses() {
        return misses;
    }

    public void setMisses(long misses) {
        this.misses = misses;
    }

    public double getMissPercentage() {
        return missPercentage;
    }

    public void setMissPercentage(double missPercentage) {
        this.missPercentage = missPercentage;
    }

    public long getPuts() {
        return puts;
    }

    public void setPuts(long puts) {
        this.puts = puts;
    }

    public long getRemovals() {
        return removals;
    }

    public void setRemovals(long removals) {
        this.removals = removals;
    }

}
