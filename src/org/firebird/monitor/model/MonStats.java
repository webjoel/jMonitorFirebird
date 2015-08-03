
package org.firebird.monitor.model;

public class MonStats {

    private int statsId;
    private Long pageReads;
    private Long pageWrites;
    private Long pageFetches;
    private Long pageMarks;
    private Long seqReads;
    private Long idxReads;
    private Long inserts;
    private Long updates;
    private Long deletes;
    private Long backouts;
    private Long purges;
    private Long expunges;

    public Long getBackouts() {
        return backouts;
    }

    public void setBackouts(Long backouts) {
        this.backouts = backouts;
    }

    public Long getDeletes() {
        return deletes;
    }

    public void setDeletes(Long deletes) {
        this.deletes = deletes;
    }

    public Long getExpunges() {
        return expunges;
    }

    public void setExpunges(Long expunges) {
        this.expunges = expunges;
    }

    public Long getIdxReads() {
        return idxReads;
    }

    public void setIdxReads(Long idxReads) {
        this.idxReads = idxReads;
    }

    public Long getInserts() {
        return inserts;
    }

    public void setInserts(Long inserts) {
        this.inserts = inserts;
    }

    public Long getPageFetches() {
        return pageFetches;
    }

    public void setPageFetches(Long pageFetches) {
        this.pageFetches = pageFetches;
    }

    public Long getPageMarks() {
        return pageMarks;
    }

    public void setPageMarks(Long pageMarks) {
        this.pageMarks = pageMarks;
    }

    public Long getPageReads() {
        return pageReads;
    }

    public void setPageReads(Long pageReads) {
        this.pageReads = pageReads;
    }

    public Long getPageWrites() {
        return pageWrites;
    }

    public void setPageWrites(Long pageWrites) {
        this.pageWrites = pageWrites;
    }

    public Long getPurges() {
        return purges;
    }

    public void setPurges(Long purges) {
        this.purges = purges;
    }

    public Long getSeqReads() {
        return seqReads;
    }

    public void setSeqReads(Long seqReads) {
        this.seqReads = seqReads;
    }

    public int getStatsId() {
        return statsId;
    }

    public void setStatsId(int statsId) {
        this.statsId = statsId;
    }

    public Long getUpdates() {
        return updates;
    }

    public void setUpdates(Long updates) {
        this.updates = updates;
    }
}
