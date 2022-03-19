package hanu.gdsc.practiceProblem.domains;

import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.domains.IdentitifedVersioningDomainObject;

public class Problem extends IdentitifedVersioningDomainObject {
    private Id coreProlemId;
    private long likeCount;
    private long dislikeCount;
    private Category category;

    public Problem(Id id, long version, Id coreProlemId, long likeCount, long dislikeCount, Category category) {
        super(id, version);
        this.coreProlemId = coreProlemId;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.category = category;
    }

    public static Problem create(Id coreProlemId) {
        return new Problem(
            Id.generateRandom(),
            0,
            coreProlemId,
            0,
            0,
            null);
    }

    public Id getCoreProlemId() {
        return coreProlemId;
    }

    public void setCoreProlemId(Id coreProlemId) {
        this.coreProlemId = coreProlemId;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
