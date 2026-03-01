package com.docvault.dto;

import java.util.List;
import java.util.Map;

public class StatsDto {
    private long total, hot, cool, cold, archive, totalMb;
    private List<Map<String, Object>> byDepartment;

    public long getTotal()                              { return total; }
    public void setTotal(long v)                        { this.total = v; }
    public long getHot()                                { return hot; }
    public void setHot(long v)                          { this.hot = v; }
    public long getCool()                               { return cool; }
    public void setCool(long v)                         { this.cool = v; }
    public long getCold()                               { return cold; }
    public void setCold(long v)                         { this.cold = v; }
    public long getArchive()                            { return archive; }
    public void setArchive(long v)                      { this.archive = v; }
    public long getTotalMb()                            { return totalMb; }
    public void setTotalMb(long v)                      { this.totalMb = v; }
    public List<Map<String, Object>> getByDepartment()  { return byDepartment; }
    public void setByDepartment(List<Map<String, Object>> v) { this.byDepartment = v; }
}