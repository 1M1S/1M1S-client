package RankingPage;

import db.Ranking;
import utils.Request;

public class RankingRequest {
    public static Ranking[] getRankings(Long interest_id){
        var request = new Request<Void, Ranking[]>("/api/ranking/top3?interest_id="+interest_id);
        return request.GET(Ranking[].class);
    }
}
