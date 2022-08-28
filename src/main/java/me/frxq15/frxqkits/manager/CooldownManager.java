package me.frxq15.frxqkits.manager;

public class CooldownManager {
    public String getPrettyTime(long duration) {
        final StringBuffer res = new StringBuffer();
        long temp = 0L;
        if (duration >= 1000L) {
            temp = duration / 86400000L;
            if (temp > 0L) {
                duration -= temp * 86400000L;
                res.append(temp).append(" day").append((temp > 1L) ? "s" : "").append((duration >= 60000L) ? ", " : "");
            }
            temp = duration / 3600000L;
            if (temp > 0L) {
                duration -= temp * 3600000L;
                res.append(temp).append(" hour").append((temp > 1L) ? "s" : "").append((duration >= 60000L) ? ", " : "");
            }
            temp = duration / 60000L;
            if (temp > 0L) {
                duration -= temp * 60000L;
                res.append(temp).append(" minute").append((temp > 1L) ? "s" : "");
            }
            if (!res.toString().equals("") && duration >= 1000L) {
                res.append(" and ");
            }
            temp = duration / 1000L;
            if (temp > 0L) {
                res.append(duration / 1000L).append(" second").append((temp > 1L) ? "s" : "");
            }
            return res.toString();
        }
        return "0 seconds";
    }
}
